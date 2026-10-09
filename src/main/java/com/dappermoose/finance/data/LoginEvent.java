package com.dappermoose.finance.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginEvent.
 *
 * @author Matt Heitker
 */
@Entity
@Table (name = "LOGIN_EVENT")
@Getter
@Setter
@EqualsAndHashCode (callSuper = true)
public class LoginEvent extends AbstractBaseEntity
{
    private static final long serialVersionUID = -5740191601882965493L;

    /**
     * The login event ID.
     *
     * @param loginEventId the new value
     * @return the login event ID
     */
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "LOGIN_EVENT_ID_SEQ")
    @SequenceGenerator (name = "LOGIN_EVENT_ID_SEQ", sequenceName = "LOGIN_EVENT_ID_SEQ", allocationSize = 1)
    @Column (name = "LOGIN_EVENT_ID", nullable = false, updatable = false)
    private Long loginEventId;

    /**
     * The user.
     *
     * @param userName the new value
     * @return the user record
     */
    @Column (name = "USER_NAME", nullable = false, length = LoginUser.USER_NAME_LEN)
    private String userName;

    /**
     * The success flag.
     *
     * @param success the new value
     * @return the success flag YES or NO
     */
    @Column (name = "SUCCESS", nullable = false, updatable = false)
    private Boolean success;
}
