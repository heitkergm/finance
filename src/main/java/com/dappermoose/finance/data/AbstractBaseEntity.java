package com.dappermoose.finance.data;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

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

    private static final long serialVersionUID = 6427988487927633526L;

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
     * The version field.
     *
     * @param version new value
     * @return value of version
     */
    @Version
    @Column (name = "VERSION", nullable = false)
    private Long version;
}
