package com.dappermoose.finance.dao;

import org.springframework.data.repository.CrudRepository;

import com.dappermoose.finance.data.LoginEvent;

/**
 * The Interface LoginEventRepository.
 */
public interface LoginEventRepository extends CrudRepository<LoginEvent, Long>
{
}
