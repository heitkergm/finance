package com.dappermoose.finance.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component ("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String>
{
    @SuppressWarnings ("null")
    @Override
    public Optional<String> getCurrentAuditor ()
    {
        // Returns the logged-in username
        return Optional.ofNullable (SecurityContextHolder.getContext ()
                .getAuthentication ())
                .filter (Authentication::isAuthenticated)
                .map (Authentication::getName);
    }
}
