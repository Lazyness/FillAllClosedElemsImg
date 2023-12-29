package prog.ua.prog.core.exception;

public class ReadImageFileException extends RuntimeException {
    public ReadImageFileException() {
    }

    public ReadImageFileException(String message) {
        super(message);
    }

    public ReadImageFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadImageFileException(Throwable cause) {
        super(cause);
    }
}
