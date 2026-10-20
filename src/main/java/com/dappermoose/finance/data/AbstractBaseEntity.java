package com.dappermoose.finance.data;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * The BaseEntity class.
 * @author Matt Heitker
 */
@MappedSuperclass
@Slf4j
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners (AuditingEntityListener.class)
public abstract class AbstractBaseEntity implements Serializable
{
    private static final long serialVersionUID = -7277883433013289299L;

    /**
     * constant size for user name in database.
     * To be used in all tables containing the user name; keeps
     * things consistent.
     */
    public static final int USER_NAME_LEN = 32;

    /**
     * The created timestamp field.
     *
     * @param created new value
     * @return value of created
     */
    @CreatedDate
    @Column (name = "CREATED_AT", nullable = false, updatable = false)
    private Instant created;

    /**
     * the created id.
     *
     * @param createdName new value
     * @return value of createdName
     */
    @CreatedBy
    @Column (name = "CREATED_BY", nullable = false, updatable = false, length = USER_NAME_LEN)
    private String createdName;

    /**
     * The version field.
     *
     * @param version new value
     * @return value of version
     */
    @Version
    @Column (name = "VERSION", nullable = false)
    private Long version;
}
