package com.dappermoose.finance.formbean;

import jakarta.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class ChangePwd.
 * @author Matt Heitker
 */
@Getter
@Setter
@EqualsAndHashCode (callSuper = true)
public class ChangePwd extends RegisterUser
{
    private static final long serialVersionUID = 1L;

    /**
     * The current password.
     *
     * @param currentPassword the new value
     * @return the value of the current password
     */
    @Size (min = 1, max = 32, message = "{changePwd.currentPassword.size}")
    private String currentPassword;
}
