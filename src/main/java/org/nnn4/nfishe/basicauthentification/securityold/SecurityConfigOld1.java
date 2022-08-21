package org.nnn4.nfishe.basicauthentification.securityold;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("old1")
@EnableWebSecurity
public class SecurityConfigOld1 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("frank")
                .password(passwordEncoder().encode("frank123"))
                .authorities("ADMIN")
                .and()
                .withUser("anna")
                .password(passwordEncoder().encode("anna123"))
                .authorities("BASIC")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .authorities("ADMIN")
//                .roles("ADMIN")
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/greeter","/api/greeter/*").authenticated()
                .antMatchers("/api/basic","/api/basic/*").hasRole("BASIC")
                .and().httpBasic();
    }
}
