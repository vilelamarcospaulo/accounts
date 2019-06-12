package service.command;

import java.math.BigDecimal;

public class CreateAccountCommand {
    String user;
    BigDecimal debtLimit;

    private CreateAccountCommand(Builder builder) {
        user = builder.user;
        debtLimit = builder.debtLimit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private String user;
        private BigDecimal debtLimit;

        private Builder() {
        }

        public Builder user(String userId) {
            this.user = userId;
            return this;
        }

        public Builder debtLimit(BigDecimal debtLimit) {
            this.debtLimit = debtLimit;
            return this;
        }

        public CreateAccountCommand build() {
            return new CreateAccountCommand(this);
        }
    }

    public String getUser() {
        return user;
    }

    public BigDecimal getDebtLimit() {
        return debtLimit;
    }
}
