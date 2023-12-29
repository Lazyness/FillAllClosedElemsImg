package prog.ua.prog.core.exception;

public class InvalidPixelValuesException extends RuntimeException {
    public InvalidPixelValuesException() {
    }

    public InvalidPixelValuesException(String message) {
        super(message);
    }

    public InvalidPixelValuesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPixelValuesException(Throwable cause) {
        super(cause);
    }
}
