package com.dappermoose.finance.init;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class defining our main program.
 *
 * @author Matt Heitker
 */
@SpringBootApplication
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@SuppressWarnings ("checkstyle:HideUtilityClassConstructor")
public final class FinanceWebApplication
{
    /**
     * actual main procedure to start our app.
     * instantiates the program.
     *
     * @param args the command line arguments
     */
    @SuppressWarnings ("checkstyle:UncommentedMain")
    public static void main (final String[] args)
    {
        TimeZone.setDefault (TimeZone.getTimeZone ("UTC"));

        SpringApplication.run (FinanceWebApplication.class, args);
    }

}
