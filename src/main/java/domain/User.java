package domain;

import domain.exceptions.BussinessExceptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;

    private String cpf; // NIN
    private String name;
    private List<Account> accounts;

    private User(String cpf, String name, List<Account> accounts) {
        this.cpf = cpf;
        this.name = name;
        this.accounts = accounts;
    }

    public String getId() { return id; }
    public String getCpf() { return cpf; }
    public String getName() { return name; }
    public List<Account> getAccounts() { return accounts; }

    public static User createUser(String cpf, String name) {
        return new User(cpf, name, new ArrayList<Account>());
    }

    public Account createAccount(BigDecimal debtLimit) throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this, debtLimit);
        this.accounts.add(account);

        return account;
    }
}
