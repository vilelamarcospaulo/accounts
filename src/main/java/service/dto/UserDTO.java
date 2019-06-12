package service.dto;

import java.util.List;

public class UserDTO {
    private String id;

    private String cpf; // NIN
    private String name;
    private List<AccountDTO> accounts;

    public UserDTO() {

    }

    public UserDTO(String cpf, String name, List<AccountDTO> accounts) {
        this.cpf = cpf;
        this.name = name;
        this.accounts = accounts;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }
}
