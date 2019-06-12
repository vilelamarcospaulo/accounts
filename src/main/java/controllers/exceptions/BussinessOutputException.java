package controllers.exceptions;

public class BussinessOutputException extends RuntimeException {
    public BussinessOutputException(String message) {
        super(message);
    }
}
