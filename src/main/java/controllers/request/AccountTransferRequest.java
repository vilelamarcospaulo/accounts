package controllers.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountTransferRequest extends RequestObject<AccountTransferRequest> {
    @NotEmpty
    private String from;

    @NotEmpty
    private String to;

    @NotNull
    private BigDecimal value;

    public AccountTransferRequest() {
        super(AccountTransferRequest.class);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
