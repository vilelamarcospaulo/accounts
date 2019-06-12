package service.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountDTO {
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal debtLimit;
    private List<AccountOperationDTO> extract;

    public AccountDTO() {

    }

    public AccountDTO(String id, String accountNumber, BigDecimal balance, BigDecimal debtLimit, List<AccountOperationDTO> extract) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.debtLimit = debtLimit;
        this.extract = extract;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getDebtLimit() {
        return debtLimit;
    }

    public void setDebtLimit(BigDecimal debtLimit) {
        this.debtLimit = debtLimit;
    }

    public List<AccountOperationDTO> getExtract() {
        return extract;
    }

    public void setExtract(List<AccountOperationDTO> extract) {
        this.extract = extract;
    }
}
