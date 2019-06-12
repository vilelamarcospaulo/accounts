package controllers.response;

public class ErrorResponse extends ResponseObject {
    String message;

    public static ErrorResponse NO_BODY = new ErrorResponse("No valid body");

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
