package domain;

import domain.accountoperations.Deposit;
import domain.exceptions.BussinessExceptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

    private User user;

    @Before
    public void createUser() {
        this.user = User.createUser("12345678912", "Jhon Doe");
    }

    @Test
    public void createNewAccount() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);

        Assert.assertEquals(account.getOwner().getName(), "Jhon Doe");
        Assert.assertEquals(account.getBalance(), BigDecimal.ZERO);
    }

    @Test(expected = BussinessExceptions.InvalidDebtLimitsForAccount.class)
    public void createNewAccountWithNegativeDebtLimit() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.valueOf(-5));

        Assert.assertEquals(account.getOwner().getName(), "Jhon Doe");
        Assert.assertEquals(account.getBalance(), BigDecimal.ZERO);
    }

    @Test(expected = BussinessExceptions.InvalidOwnerToAccount.class)
    public void createNewAccountWithoutOwner() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(null, BigDecimal.ZERO);
    }

    @Test
    public void depositOnAccount() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        Deposit deposit = account.deposit(BigDecimal.TEN);

        Assert.assertEquals(account.getExtract().size(), 1L);
        Assert.assertEquals(account.getExtract().get(0), deposit);
        Assert.assertEquals(deposit.getType(), "DEPOSIT");
        Assert.assertEquals(deposit.getOrigin(), "unknow");


        Assert.assertEquals(account.getBalance(), BigDecimal.TEN);

        account.deposit(BigDecimal.TEN);
        Assert.assertEquals(account.getBalance(), BigDecimal.valueOf(20L));

        Assert.assertEquals(account.getExtract().size(), 2L);
        Assert.assertEquals(account.getExtract().get(1).getType(), "DEPOSIT");
    }

    @Test(expected = BussinessExceptions.InvalidValueToOperation.class)
    public void depositOnAccountNegativaValue() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        account.deposit(BigDecimal.valueOf(-10));
    }

    @Test
    public void withdrawOnAccount() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForWithdraw, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        account.deposit(BigDecimal.valueOf(10));
        account.withdraw(BigDecimal.valueOf(5));

        Assert.assertEquals(account.getExtract().size(), 2L);
        Assert.assertEquals(account.getExtract().get(0).getType(), "DEPOSIT");
        Assert.assertEquals(account.getExtract().get(1).getType(), "WITHDRAW");

        Assert.assertEquals(account.getBalance(), BigDecimal.valueOf(5));
    }

    @Test
    public void withdrawAllBalance() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForWithdraw, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        account.deposit(BigDecimal.valueOf(10));
        account.withdraw(BigDecimal.valueOf(10));

        Assert.assertEquals(account.getBalance(), BigDecimal.ZERO);
    }

    @Test(expected = BussinessExceptions.InsulficientFoundsForWithdraw.class)
    public void withdrawOnAccountNoLimit() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForWithdraw, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        account.deposit(BigDecimal.valueOf(10));
        account.withdraw(BigDecimal.valueOf(15));
    }

    @Test
    public void tranferTo() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        User user2 = User.createUser("123", "Mariah Doe");

        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        Account account2 = Account.createAccount(user2, BigDecimal.ZERO);

        account.deposit(BigDecimal.TEN);
        account.tranferTo(account2, BigDecimal.valueOf(5));

        Assert.assertEquals(2l, account.getExtract().size());
        Assert.assertEquals(1l, account2.getExtract().size());

        Assert.assertEquals(account.getExtract().get(1).getType(), "TRANSFERENCE");
        Assert.assertEquals(account2.getExtract().get(0).getType(), "DEPOSIT");

        Assert.assertEquals(((Deposit)account2.getExtract().get(0)).getOrigin(), account.getAccountNumber());

    }

    @Test(expected = BussinessExceptions.InvalidAccountToTransfer.class)
    public void tranferToSameAccount() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        Account account = Account.createAccount(this.user, BigDecimal.ZERO);

        account.deposit(BigDecimal.TEN);
        account.tranferTo(account, BigDecimal.valueOf(5));

    }

    @Test(expected = BussinessExceptions.InsulficientFoundsForTransfer.class)
    public void tranferToWithouFounds() throws BussinessExceptions.InvalidValueToOperation, BussinessExceptions.InsulficientFoundsForTransfer, BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount, BussinessExceptions.InvalidAccountToTransfer {
        User user2 = User.createUser("123", "Mariah Doe");

        Account account = Account.createAccount(this.user, BigDecimal.ZERO);
        Account account2 = Account.createAccount(user2, BigDecimal.ZERO);

        account.tranferTo(account2, BigDecimal.TEN);
    }
}