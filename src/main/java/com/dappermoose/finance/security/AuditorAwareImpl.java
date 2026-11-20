package com.dappermoose.finance.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component ("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String>
{
    @Override
    public Optional<String> getCurrentAuditor ()
    {
        Authentication authentication = SecurityContextHolder.getContext ().getAuthentication ();

        log.debug ("auditor-provider - authentication + " + authentication);
        if (authentication == null ||
            !authentication.isAuthenticated () ||
            authentication instanceof AnonymousAuthenticationToken)
        {
            return Optional.of ("SYSTEM");
        }

        // Returns the logged-in username if not null
        return Optional.of (authentication.getName ());
    }
}
