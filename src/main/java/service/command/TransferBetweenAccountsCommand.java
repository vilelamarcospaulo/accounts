package service.command;

import java.math.BigDecimal;

public class TransferBetweenAccountsCommand {
    private String from;
    private String to;
    private BigDecimal value;

    private TransferBetweenAccountsCommand(Builder builder) {
        from = builder.from;
        to = builder.to;
        value = builder.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String from;
        private String to;
        private BigDecimal value;

        private Builder() {
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public TransferBetweenAccountsCommand build() {
            return new TransferBetweenAccountsCommand(this);
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getValue() {
        return value;
    }
}
