package service.command;

public class CreateUserCommand {
    String cpf;
    String name;

    private CreateUserCommand(Builder builder) {
        cpf = builder.cpf;
        name = builder.name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }


    public static final class Builder {
        private String cpf;
        private String name;

        private Builder() {
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public CreateUserCommand build() {
            return new CreateUserCommand(this);
        }
    }
}
