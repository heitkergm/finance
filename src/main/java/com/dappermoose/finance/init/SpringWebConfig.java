package com.dappermoose.finance.init;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import org.jspecify.annotations.NonNull;

/**
 * The Class SpringWebConfig.
 *
 * @author Matt Heitker
 */
@ComponentScan ({ "com.dappermoose.finance.data",
                  "com.dappermoose.finance.init"})
@EnableJpaRepositories (basePackages = "com.dappermoose.finance.dao")
@Configuration
@EntityScan ("com.dappermoose.finance.data")
public class SpringWebConfig implements WebMvcConfigurer
{
    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
     * #addInterceptors(org.springframework.web.servlet.config.annotation.
     * InterceptorRegistry)
     */
    @Override
    public void addInterceptors (@NonNull final InterceptorRegistry registry)
    {
        registry.addInterceptor (new LocaleChangeInterceptor ());
    }

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
     * Session locale resolver.
     *
     * @return the session locale resolver
     */
    @Bean
    SessionLocaleResolver localeResolver ()
    {
        final SessionLocaleResolver resolver = new SessionLocaleResolver ();
        return resolver;
    }

    // beans for tx/database
    /**
     * Persistence post processor.
     *
     * @return the persistence exception translation post processor
     */
    @Bean
    public static PersistenceExceptionTranslationPostProcessor persistencePostProcessor ()
    {
        return new PersistenceExceptionTranslationPostProcessor ();
    }
}
