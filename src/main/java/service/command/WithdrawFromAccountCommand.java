package service.command;

import java.math.BigDecimal;

public class WithdrawFromAccountCommand {
    private String account;
    private BigDecimal value;

    private WithdrawFromAccountCommand(WithdrawFromAccountCommand.Builder builder) {
        account = builder.account;
        value = builder.value;
    }

    public static WithdrawFromAccountCommand.Builder newBuilder() {
        return new WithdrawFromAccountCommand.Builder();
    }

    public static final class Builder {
        private String account;
        private BigDecimal value;

        private Builder() {
        }

        public WithdrawFromAccountCommand.Builder account(String account) {
            this.account = account;
            return this;
        }

        public WithdrawFromAccountCommand.Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public WithdrawFromAccountCommand build() {
            return new WithdrawFromAccountCommand(this);
        }
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getValue() {
        return value;
    }
}
