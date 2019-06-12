package domain.accountoperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AccountOperation {
    private String id;
    private String type;
    private BigDecimal value;
    private LocalDateTime time;

    protected AccountOperation(String id, String type, LocalDateTime time, BigDecimal value) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public BigDecimal getValue() {
        return value;
    }

}
