package domain;

import datastore.Id;
import domain.accountoperations.AccountOperation;
import domain.accountoperations.Deposit;
import domain.accountoperations.Transference;
import domain.accountoperations.Withdraw;
import domain.exceptions.BussinessExceptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {

    @Id
    private String accountNumber;

    private User owner;
    private BigDecimal balance;
    private BigDecimal debtLimit;
    private List<AccountOperation> extract;

    private Account(String accountNumber, User owner, BigDecimal balance, BigDecimal debtLimit, List<AccountOperation> extract) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.debtLimit = debtLimit;
        this.extract = extract;
    }

    public String getAccountNumber() { return accountNumber; }
    public User getOwner() {
        return owner;
    }
    public BigDecimal getBalance() { return balance; }
    public BigDecimal getDebtLimit() { return debtLimit; }
    public List<AccountOperation> getExtract() { return extract; }

    //

    public static Account createAccount(User owner, BigDecimal debtLimit) throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        if(owner == null) {
            throw new BussinessExceptions.InvalidOwnerToAccount();
        }

        if(BigDecimal.ZERO.compareTo(debtLimit) > 0) {
            throw new BussinessExceptions.InvalidDebtLimitsForAccount();
        }

        return new Account(UUID.randomUUID().toString(), owner, BigDecimal.ZERO, debtLimit, new ArrayList<>());
    }

    public Deposit deposit(BigDecimal value) throws BussinessExceptions.InvalidValueToOperation {
       return this.deposit(value, "unknow");
    }

    public Deposit deposit(BigDecimal value, String origin) throws BussinessExceptions.InvalidValueToOperation {
        this.validateOperationValueGreatterThanZero(value, "deposit");
        this.balance = value.add(this.balance);

        Deposit deposit = Deposit.builDeposit(this, value, origin);
        this.addToExtractHistory(deposit);

        return deposit;
    }

    public Withdraw withdraw(BigDecimal value) throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForWithdraw {
        this.validateOperationValueGreatterThanZero(value, "withdraw");
        this.withdrawMoneyFromAccount(value);

        Withdraw withdraw = Withdraw.builWithdraw(this, value);
        this.addToExtractHistory(withdraw);

        return withdraw;
    }

    public Transference tranferTo(Account to, BigDecimal value) throws BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InvalidAccountToTransfer {
        if(this == to) {
            throw new BussinessExceptions.InvalidAccountToTransfer();
        }

        this.validateOperationValueGreatterThanZero(value, "withdraw");

        try {
            this.withdrawMoneyFromAccount(value);
            to.deposit(value, this.accountNumber);

        } catch (BussinessExceptions.InsulficientFoundsForWithdraw insulficientFoundsForWithdraw) {
            throw new BussinessExceptions.InsulficientFoundsForTransfer();
        }

        Transference transference = Transference.buildTranference(to.getAccountNumber(), value);
        this.addToExtractHistory(transference);

        return transference;
    }

    private void validateOperationValueGreatterThanZero(BigDecimal value, String operation) throws BussinessExceptions.InvalidValueToOperation {
        if(value == null || value.compareTo(BigDecimal.ZERO) < 1) {
            BussinessExceptions.InvalidValueToOperation ex = new BussinessExceptions.InvalidValueToOperation();
            ex.addParametter(operation, "null or less than zero");

            throw ex;
        }
    }

    private void withdrawMoneyFromAccount(BigDecimal value) throws BussinessExceptions.InsulficientFoundsForWithdraw {
        BigDecimal newBalance = this.balance.subtract(value);
        if(newBalance.compareTo(BigDecimal.valueOf(-1).multiply(this.debtLimit)) < 0) {
            throw new BussinessExceptions.InsulficientFoundsForWithdraw();
        }

        this.balance = newBalance;
    }

    private void addToExtractHistory(AccountOperation operation) {
        this.extract.add(operation);
    }

}
