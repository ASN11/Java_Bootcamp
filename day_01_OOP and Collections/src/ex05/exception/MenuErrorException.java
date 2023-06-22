package exception;

public class MenuErrorException extends RuntimeException {
    public MenuErrorException(String insufficient_balance) {
        super(insufficient_balance);
    }

}
