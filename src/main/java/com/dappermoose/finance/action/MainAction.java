package com.dappermoose.finance.action;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dappermoose.finance.dao.AccountRepository;
import com.dappermoose.finance.dao.LoginUserRepository;
import com.dappermoose.finance.data.Account;
import com.dappermoose.finance.data.LoginUser;

// TODO: Auto-generated Javadoc
/**
 * The Class MainAction.
 *
 * @author Matt Heitker
 */
@Controller
public class MainAction
{
    @Inject
    private AccountRepository accountRepository;

    @Inject
    private LoginUserRepository loginRepository;

    /**
     * Main action.
     *
     * @param model
     *            - the model to display
     * @param principal
     *            - the user principal from spring security
     *
     * @return the string
     */
    @RequestMapping ("/main")
    public String mainAction (final Model model, final Principal principal)
    {
        final List<LoginUser> logins = loginRepository.findByUserName (principal.getName ());
        final List<Account> accounts = accountRepository.findByUser (logins.get (0));
        model.addAttribute ("accounts", accounts);

        // get the overall balance.
        BigDecimal balance = BigDecimal.ZERO.setScale (2);
        for (final Account acct : accounts)
        {
            balance = balance.add (acct.getStartingBalance ().setScale (2));
        }
        model.addAttribute ("balance", balance);

        return "main";
    }
}
