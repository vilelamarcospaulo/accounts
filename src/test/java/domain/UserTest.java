package domain;

import domain.exceptions.BussinessExceptions;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class UserTest {

    @Test
    public void createUser() {
        User user = User.createUser("123456789123", "Jhon Doe");

        Assert.assertEquals(user.getCpf(), "123456789123");
        Assert.assertEquals(user.getName(), "Jhon Doe");
        Assert.assertEquals(user.getAccounts().size(), 0L);

    }

    @Test
    public void createAccount() throws BussinessExceptions.InvalidDebtLimitsForAccount, BussinessExceptions.InvalidOwnerToAccount {
        User user = User.createUser("123456789123", "Jhon Doe");
        Account account = user.createAccount(BigDecimal.TEN);

        Assert.assertEquals(user.getAccounts().size(), 1L);
        Assert.assertEquals(account.getBalance(), BigDecimal.ZERO);
        Assert.assertEquals(account.getDebtLimit(), BigDecimal.TEN);
        Assert.assertEquals(account.getOwner(),  user);
    }

}