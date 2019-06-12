package domain.accountoperations;

import domain.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Withdraw extends AccountOperation {
    public Withdraw(String id, String type, LocalDateTime time, BigDecimal value) {
        super(id, type, time, value);
    }

    public static Withdraw builWithdraw(Account from, BigDecimal value) {
        return new Withdraw(UUID.randomUUID().toString(), "WITHDRAW", LocalDateTime.now(), value);
    }
}
