package exception;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String insufficient_balance) {
        super(insufficient_balance);
    }
}
