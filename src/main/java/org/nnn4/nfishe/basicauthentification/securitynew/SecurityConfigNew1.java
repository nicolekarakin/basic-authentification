package org.nnn4.nfishe.basicauthentification.securitynew;

import lombok.RequiredArgsConstructor;
import org.nnn4.nfishe.basicauthentification.config.UrlMapping;
import org.nnn4.nfishe.basicauthentification.account.Account;
import org.nnn4.nfishe.basicauthentification.account.ERole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashSet;
@Profile("new1")
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfigNew1 implements WebSecurityCustomizer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean //protected void configure(HttpSecurity http) throws Exception << with WebSecurityConfigurerAdapter
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(UrlMapping.PUBLIC+"**").permitAll()
                .antMatchers(UrlMapping.GREETER+"**").authenticated()
                .antMatchers(UrlMapping.BASIC+"/*","/api/basic").hasAnyAuthority("BASIC")//hasRole("BASIC")//
                .antMatchers(UrlMapping.BASIC+"/*/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
        ;

        return http.build();
    }

    @Bean //protected void configure(AuthenticationManagerBuilder auth) throws Exception
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user1=new Account(passwordEncoder().encode("frank123"),"frank",
                new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(ERole.ADMIN.name()))),true);
        UserDetails user2=new Account(passwordEncoder().encode("anna123"),"anna",
                new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(ERole.BASIC.name()))),true);
        UserDetails user3=new Account(passwordEncoder().encode("annafrank"),"annafrank",
                new HashSet<>(Arrays.asList(new SimpleGrantedAuthority(ERole.BASIC.name()),
                        new SimpleGrantedAuthority(ERole.ADMIN.name()))),true);


        UserDetails user0 = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password") )
                .authorities(ERole.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(user1,user2,user3,user0);
    }

    @Override
    public void customize(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
}
