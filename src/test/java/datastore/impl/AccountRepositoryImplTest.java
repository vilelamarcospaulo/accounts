package datastore.impl;

import datastore.AccountRepository;
import domain.Account;
import domain.User;
import domain.exceptions.BussinessExceptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class AccountRepositoryImplTest {
    @Before
    public void setup() {
        AccountRepositoryImpl.initializeInstance();
    }

    @Test
    public void save() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

        User user = User.createUser("123", "Jhon Doe");
        Account account = Account.createAccount(user, BigDecimal.TEN);

        Account savedAccount = accountRepository.save(account);

        Assert.assertNotNull(account);
        Assert.assertEquals(account.getAccountNumber(), savedAccount.getAccountNumber());
    }

    @Test
    public void find() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

        Account account = this.saveTestAccount();

        Optional<Account> getAccount = accountRepository.find(account.getAccountNumber());
        Assert.assertTrue(getAccount.isPresent());
    }

    @Test
    public void findNoAccount() {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

        Optional<Account> getAccount = accountRepository.find("123");
        Assert.assertFalse(getAccount.isPresent());
    }

    @Test
    public void deleteAccount() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
        Account savedAccount = this.saveTestAccount();
        Account deletedAccount = accountRepository.delete(savedAccount.getAccountNumber());

        Assert.assertEquals(savedAccount, deletedAccount);
        Assert.assertFalse(accountRepository.find(deletedAccount.getAccountNumber()).isPresent());
    }

    @Test
    public void deleteNotExistentAccount() {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

        Account deletedAccount = accountRepository.delete("123");
        Assert.assertNull(deletedAccount);
    }

    @Test
    public void findAll() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

        List<Account> accounts = Arrays.asList(this.saveTestAccount(), this.saveTestAccount());
        List<Account> accountsFromBase = accountRepository.getAll();

        Assert.assertEquals(accounts.size(), accountsFromBase.size());
        accountsFromBase.forEach(it -> Assert.assertTrue(accounts.contains(it)));
    }


    private Account saveTestAccount() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

        User user = User.createUser("123", "Jhon Doe");
        Account account = Account.createAccount(user, BigDecimal.TEN);

        accountRepository.save(account);

        return account;
    }
}