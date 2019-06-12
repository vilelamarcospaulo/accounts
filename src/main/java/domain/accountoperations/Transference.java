package domain.accountoperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transference extends AccountOperation {
    private String to;

    public Transference(String id, String type, LocalDateTime time, String to, BigDecimal value) {
        super(id, type, time, value);

        this.to = to;
    }

    public static Transference buildTranference(String to, BigDecimal value) {
        return new Transference(UUID.randomUUID().toString(), "TRANSFERENCE", LocalDateTime.now(), to, value);
    }

    public String getTo() {
        return to;
    }
}
