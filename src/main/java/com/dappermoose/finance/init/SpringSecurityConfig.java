package com.dappermoose.finance.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *Spring Security config.
 *
 * @author Matt Heitker
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{
    /**
     * make an argon2 password encoder.
     *
     * @return the password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder ()
    {
        return new Argon2Password4jPasswordEncoder ();
    }

    /**
     * security filtering.
     *
     * @param http - the HttpSecurity object
     * @return - the security filter chain object
     */
    @Bean
    SecurityFilterChain securityFilterChain (final HttpSecurity http) throws Exception
    {
        http
            .formLogin (formLogin ->
                formLogin.loginPage ("/login").permitAll ()
            )
            .authorizeHttpRequests (authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers ("/images/**", "/css/**", "/**/favicon.ico",
                                      "/webjars/**", "/register","/error").permitAll ()
                    .anyRequest ().authenticated ()
            )
            .logout (logout ->
                logout.invalidateHttpSession (true).logoutSuccessUrl ("/main")
                      .clearAuthentication (true)
            )
            .sessionManagement (sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy (SessionCreationPolicy.ALWAYS)
                    .sessionFixation ()
            )
        ;
        return http.build ();
    }
}
