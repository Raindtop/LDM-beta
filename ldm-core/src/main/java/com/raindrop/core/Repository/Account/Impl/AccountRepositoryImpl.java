package com.raindrop.core.Repository.Account.Impl;

import com.raindrop.core.Mapper.Account.AccountMapper;
import com.raindrop.core.Repository.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountMapper accountMapper;

}
