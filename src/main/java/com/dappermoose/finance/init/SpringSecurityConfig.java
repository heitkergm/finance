
package com.dappermoose.finance.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;

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
}
