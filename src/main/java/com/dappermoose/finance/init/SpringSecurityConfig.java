package com.dappermoose.finance.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dappermoose.finance.security.AuthenticationListener;

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
     * Authentication Listener.
     *
     * @return the authentication listener
     */
    @Bean
    AuthenticationListener authenticationListener ()
    {
        return new AuthenticationListener ();
    }

    /**
     * security for the actuator filtering.
     *
     * @param http - the HttpSecurity object
     * @return - the security filter chain object
     */
    @Bean
    @Order (1)
    SecurityFilterChain actuatorFilterChain (final HttpSecurity http) throws Exception
    {
        http
            .securityMatcher ("/actuator/**")
            .authorizeHttpRequests (actuatorRequest ->
                actuatorRequest.anyRequest ().permitAll ()) 
            // Disable CSRF globally for Actuator access
            .csrf (csrf -> csrf.disable ());
        return http.build ();
    }

    /**
     * security for the app filtering.
     *
     * @param http - the HttpSecurity object
     * @return - the security filter chain object
     */
    @Bean
    @Order (2)
    SecurityFilterChain securityFilterChain (final HttpSecurity http) throws Exception
    {
        http
            .formLogin (formLogin ->
                formLogin.loginPage ("/login").permitAll ()
            )
            .authorizeHttpRequests (authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers ("/images/**", "/css/**",
                        "/webjars/**", "/register", "/error").permitAll ()
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
