package com.dappermoose.finance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dappermoose.finance.data.LoginUser;


/**
 * The Interface LoginUserRepository.
 *
 * @author Matt Heitker
 */
public interface LoginUserRepository extends JpaRepository<LoginUser, Long>
{

    /**
     * Find by user name.
     *
     * @param userName the user name
     * @return the list
     */
    List<LoginUser> findByUserName (String userName);
}
