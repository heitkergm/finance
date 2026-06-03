package com.dappermoose.finance.init;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.jspecify.annotations.NonNull;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class SpringWebConfig.
 *
 * @author Matt Heitker
 */
@Slf4j
@ComponentScan ({ "com.dappermoose.finance.data",
                  "com.dappermoose.finance.init"})
@EnableJpaRepositories (basePackages = "com.dappermoose.finance.dao")
@EnableJpaAuditing
@Configuration
@EntityScan ("com.dappermoose.finance.data")
public class SpringWebConfig implements WebMvcConfigurer
{
    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
     * #addViewControllers(org.springframework.web.servlet.config.annotation.
     * ViewControllerRegistry)
     */
    @Override
    public void addViewControllers (@NonNull final ViewControllerRegistry registry)
    {
        registry.addRedirectViewController ("/", "/main");
        registry.addViewController ("/login");
    }

    // beans for i18n
    /**
     * Message source.
     *
     * @return the message source
     */
    @Bean
    MessageSource messageSource ()
    {
        final ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource ();
        source.setCacheSeconds (60);
        source.setBasenames ("classpath:messages", "classpath:ValidationMessages");
        return source;
    }

    /**
     * bean to hold all the locale names.
     *
     * @return the array of locale names
     */
    @Bean (name = "localeNames")
    public String[] localeNames ()
    {
        // only list supported ones
        return new String [] {"en_us"};
    }
}
