package controllers.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountCreateRequest extends RequestObject<AccountCreateRequest> {
    @NotEmpty
    private String user;

    @NotNull
    private BigDecimal debtLimit;

    public AccountCreateRequest() {
        super(AccountCreateRequest.class);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getDebtLimit() {
        return debtLimit;
    }

    public void setDebLimit(BigDecimal debLimit) {
        this.debtLimit = debLimit;
    }
}
