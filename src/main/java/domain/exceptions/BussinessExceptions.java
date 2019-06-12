package domain.exceptions;

public class BussinessExceptions {
    public static class InvalidValueToOperation extends BussinessException {
        public InvalidValueToOperation() {
            super("INV01", "Invalid value to operation %s, value can`t be %s");
        }
    }

    public static class InsulficientFoundsForWithdraw extends BussinessException {
        public InsulficientFoundsForWithdraw() {
            super("LIM01", "Can`t withdraw this value, no limit");
        }
    }

    public static class InsulficientFoundsForTransfer extends BussinessException {
        public InsulficientFoundsForTransfer() {
            super("LIM02", "Can`t transfer this value, no suficient founds");
        }
    }

    public static class InvalidDebtLimitsForAccount extends BussinessException {
        public InvalidDebtLimitsForAccount() {
            super("LIM03", "Debt limit for account must be greatter or equal to zero");
        }
    }

    public static class InvalidOwnerToAccount extends BussinessException {
        public InvalidOwnerToAccount() {
            super("INV03", "Account owner can`t be null");
        }
    }

    public static class InvalidAccountToTransfer extends BussinessException {
        public InvalidAccountToTransfer() {
            super("INV04", "Can`t transfer to same account of origin");
        }
    }
}