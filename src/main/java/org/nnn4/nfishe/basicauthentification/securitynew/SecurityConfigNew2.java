package org.nnn4.nfishe.basicauthentification.securitynew;

import lombok.RequiredArgsConstructor;
import org.nnn4.nfishe.basicauthentification.config.UrlMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@Profile("new2")
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class SecurityConfigNew2 implements WebSecurityCustomizer {

//    private final UserDetails userDetails;
    private final UserDetailsService userDetailsService;
//    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authenticationJwtTokenFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }

    @Bean //protected void configure(HttpSecurity http) throws Exception << with WebSecurityConfigurerAdapter
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers(UrlMapping.PUBLIC+"**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/shopper/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/shopper/search").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/mitshopper/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/mitshopper/search").permitAll()
                .antMatchers(UrlMapping.BASIC+"/*/**").permitAll()
                // Our private endpoints
                .antMatchers(UrlMapping.GREETER+"**").authenticated()
                .antMatchers(UrlMapping.BASIC+"/*","/api/basic").hasAnyAuthority("BASIC")//hasRole("BASIC")//
                .anyRequest().authenticated()
//                .and().httpBasic()
        ;

        // Add JWT token filter
        http.addFilterBefore(
                authenticationJwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }


    @Override
    public void customize(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("*");
            }
        };
    }
}

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

//https://medium.com/swlh/stateless-jwt-authentication-with-spring-boot-a-better-approach-1f5dbae6c30f