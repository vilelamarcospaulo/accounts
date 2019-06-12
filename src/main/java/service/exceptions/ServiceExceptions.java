package service.exceptions;

public class ServiceExceptions {
    public static class AccountNotFound extends ServiceException {
        public AccountNotFound () {
            super("NOF01", "Account %s not found");
        }
    }
    public static class UserNotFound extends ServiceException {
        public UserNotFound (String userId) {
            super("NOF02", "User %s not found");
            this.addParametter(userId);
        }
    }

}