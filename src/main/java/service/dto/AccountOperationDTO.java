package service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountOperationDTO {
    private String id;
    private String type;
    private BigDecimal value;
    private LocalDateTime time;

    public AccountOperationDTO() {}

    protected AccountOperationDTO(String id, String type, LocalDateTime time, BigDecimal value) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
