package domain.accountoperations;

import domain.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Deposit extends AccountOperation {
    String origin;

    public Deposit(String id, String type, Account account, LocalDateTime time, BigDecimal value, String origin) {
        super(id, type, time, value);

        this.origin = origin;
    }

    public static Deposit builDeposit(Account from, BigDecimal value, String origin) {
        return new Deposit(UUID.randomUUID().toString(), "DEPOSIT", from, LocalDateTime.now(), value, origin);
    }

    public String getOrigin() { return origin; }
}
