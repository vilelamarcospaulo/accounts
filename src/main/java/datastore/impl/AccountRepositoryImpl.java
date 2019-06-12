package datastore.impl;

import datastore.AccountRepository;
import domain.Account;


public class AccountRepositoryImpl extends  RepositoryImpl<Account> implements AccountRepository {
    private static final AccountRepository accountRepository = new AccountRepositoryImpl();

    private AccountRepositoryImpl() {}

    public static AccountRepository getInstance() { return AccountRepositoryImpl.accountRepository; }
}
