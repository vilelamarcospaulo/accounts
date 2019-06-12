package controllers.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class WithdrawRequest extends RequestObject<WithdrawRequest> {
    @NotEmpty
    private String from;

    @NotNull
    private BigDecimal value;

    public WithdrawRequest() {
        super(WithdrawRequest.class);
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
