package prog.ua.prog.core.exception;

public class DanglingOutlineException extends RuntimeException {
    public DanglingOutlineException() {
    }

    public DanglingOutlineException(String message) {
        super(message);
    }

    public DanglingOutlineException(String message, Throwable cause) {
        super(message, cause);
    }

    public DanglingOutlineException(Throwable cause) {
        super(cause);
    }
}
