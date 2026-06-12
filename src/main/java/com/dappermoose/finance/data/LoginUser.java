package com.dappermoose.finance.data;

import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * The Class LoginUser.
 * This is a registered user.
 *
 * @author Matt Heitker
 */
@Entity
@Table (name = "LOGIN_USER", uniqueConstraints = @UniqueConstraint (columnNames = "USER_NAME", name = "unique_user_name"))
@Cache (usage = CacheConcurrencyStrategy.READ_WRITE, region = "users")
@Slf4j
@Getter
@Setter
@EqualsAndHashCode (callSuper = true)
public class LoginUser extends AbstractBaseModifiableEntity
{
    private static final long serialVersionUID = 9223372036854775807L;

    @Inject
    private static PasswordEncoder passwordEncoder;

    /**
     *  The user id.
     *
     * @param userId The new value for the user ID.
     * @return the User ID.
     */
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator (name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @Column (name = "USER_ID", nullable = false)
    private Long userId;

    /**
     * The user name.
     *
     * @param userName - the new user Name
     * @return the user name.
     */
    @Column (name = "USER_NAME", nullable = false, length = 32)
    private String userName;

    // passwords are stored as encoded hash values
    /**
     * The password.
     *
     * @return the encrypted password
     */
    @Column (name = "PASSWORD", nullable = false, length = 255)
    private String password;

    /**
     * The locale name for this user.
     *
     * @param localeName - the new value of te locale name
     * @return the Locale for this user
     */
    @Column (name = "LOCALE_NAME", nullable =  false, length = 32)
    private String localeName;

    /**
     * is this user enabled.
     *
     * @param enabled the new YesNo value for enabled.
     * @return the YesNo value for enabled.
     */
    @Column (name = "ENABLED", nullable = false)
    private Boolean enabled;

    // password handling

    /**
     * Validate the password.
     *
     * @param inputPassword - the supplied password
     * @return true/false - the password is correct
     */
    public boolean checkpw (final String inputPassword)
    {
        return passwordEncoder.matches (inputPassword, password);
    }

    /**
     * Sets the password.
     *
     * @param passwordNew the new password
     */
    public void setPassword (final String passwordNew)
    {
        password = passwordEncoder.encode (passwordNew);
    }
}
