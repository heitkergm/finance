package com.dappermoose.finance.action;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dappermoose.finance.dao.LoginUserRepository;
import com.dappermoose.finance.data.LoginUser;
import com.dappermoose.finance.formbean.ChangePwd;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ChangePasswordAction.
 * @author Matt Heitker
 */
@Controller
@Slf4j
@RequestMapping ("/changepwd")
public class ChangePasswordAction
{
    /** The user repository. */
    @Inject
    private LoginUserRepository userRepository;

    /** The message source. */
    @Inject
    private MessageSource messageSource;

    /** password encoder. */
    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * Main action. Display the form.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping
    public String mainAction (final Model model)
    {
        final ChangePwd changepwd = new ChangePwd ();
        String userName = ((User) SecurityContextHolder.getContext ()
                .getAuthentication ().getPrincipal ()).getUsername ();

        List<LoginUser> users = userRepository.findByUserName (userName);

        LoginUser user = users.get (0);
        changepwd.setUserName (user.getUserName ());

        model.addAttribute ("changepwd", changepwd);
        return "changepwd";
    }

    /**
     * Process change password action.
     *
     * @param changepwd the change password form data
     * @param res the res
     * @param model the model
     * @param request the request
     * @return the string
     */
    @PostMapping
    public String processRegisterAction (
            @Valid @ModelAttribute ("changepwd") final ChangePwd changepwd,
            final BindingResult res, final Model model,
            final HttpServletRequest request)
    {
        if (res.hasErrors ())
        {
            model.addAttribute ("changepwd", changepwd);
            return "changepwd";
        }

        // check the current password, etc.
        String userName = ((User) SecurityContextHolder.getContext ()
                .getAuthentication ().getPrincipal ()).getUsername ();

        List<LoginUser> users = userRepository.findByUserName (userName);

        LoginUser user = users.get (0);

        if (!passwordEncoder.matches (changepwd.getCurrentPassword (), user.getPassword ()))
        {
            final ObjectError objErr = new ObjectError ("User",
                    messageSource.getMessage ("changepwd.currpwd.bad", null,
                            request.getLocale ()));
            res.addError (objErr);
            model.addAttribute ("changepwd", changepwd);
            return "changepwd";
        }

        // modify user bean
        user.setPassword (passwordEncoder.encode (changepwd.getPassword ()));
        userRepository.save (user);

        // logout
        try
        {
            request.logout ();
        }
        catch (ServletException se)
        {
            log.info ("Servlet exception upon logout " + se.getMessage ());
        }

        return "redirect:/main";
    }
}
