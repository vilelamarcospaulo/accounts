package controllers.request;

import javax.validation.constraints.NotEmpty;

public class UserPostRequest extends RequestObject<UserPostRequest> {
    @NotEmpty
    String cpf;

    @NotEmpty
    String name;

    public UserPostRequest() {
        super(UserPostRequest.class);
    }

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

}
