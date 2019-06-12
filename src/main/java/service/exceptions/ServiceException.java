package service.exceptions;

public class ServiceException extends Exception {
    private String code;
    private String message;

    public ServiceException(String code, String message) {
        super(String.format("%s - %s", code, message));

        this.code = code;
        this.message = message;
    }

    public String fullMessage() { return String.format("%s - %s", code, message); }

    public void addParametter(Object ... parameters) {
        this.message = String.format(this.message, parameters);
    }
}
