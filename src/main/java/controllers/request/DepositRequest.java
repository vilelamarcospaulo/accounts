package controllers.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositRequest extends RequestObject<DepositRequest> {
    @NotEmpty
    private String to;

    @NotNull
    private BigDecimal value;

    public DepositRequest() {
        super(DepositRequest.class);
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
