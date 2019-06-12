package service.command;

import java.math.BigDecimal;

public class DepositOnAccountCommand {
    private String account;
    private BigDecimal value;

    private DepositOnAccountCommand(Builder builder) {
        account = builder.account;
        value = builder.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String account;
        private BigDecimal value;

        private Builder() {
        }

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public DepositOnAccountCommand build() {
            return new DepositOnAccountCommand(this);
        }
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getValue() {
        return value;
    }
}
