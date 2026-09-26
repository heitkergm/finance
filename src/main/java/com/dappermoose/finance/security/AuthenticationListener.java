package com.dappermoose.finance.security;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

import org.jspecify.annotations.NonNull;

import com.dappermoose.finance.dao.LoginEventRepository;
import com.dappermoose.finance.dao.LoginUserRepository;
import com.dappermoose.finance.data.LoginEvent;
import com.dappermoose.finance.data.LoginUser;

import lombok.extern.slf4j.Slf4j;

/**
 * listening for authentication events and logging them to the login event table.
 *
 * @author matthewheitker
 */

@Slf4j
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent>
{
    @Inject
    private LoginUserRepository loginUserRepository;

    @Inject
    private LoginEventRepository loginEventRepository;

    @Override
    @Transactional
    public void onApplicationEvent (@NonNull final AbstractAuthenticationEvent e)
    {
        // deepcode ignore LogLevelCheck: don't care
        log.debug ("login event + " + e.toString ());

        if (!(e instanceof AuthenticationSuccessEvent ||
              e instanceof AbstractAuthenticationFailureEvent))
        {
            return;
        }

        Object obj = e.getAuthentication ().getPrincipal ();

        String userName;
        if (obj instanceof String)
        {
            userName = (String) obj;
        }
        else if (obj instanceof UserDetails)
        {
            userName = ((UserDetails) obj).getUsername ();
        }
        else
        {
            userName = "SYSTEM";
        }

        List<LoginUser> luser = loginUserRepository.findByUserName (userName);

        if (luser.size () <= 0)
        {
            return;
        }

        LoginUser user = luser.get (0);

        LoginEvent loginEvent = new LoginEvent ();

        loginEvent.setUser (user);

        if (e instanceof AbstractAuthenticationFailureEvent)
        {
            loginEvent.setSuccess (Boolean.FALSE);
        }
        else
        {
            loginEvent.setSuccess (Boolean.TRUE);
        }
        loginEventRepository.save (loginEvent);
    }
}
