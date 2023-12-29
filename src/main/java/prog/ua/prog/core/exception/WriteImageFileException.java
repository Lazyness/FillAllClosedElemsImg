package prog.ua.prog.core.exception;

public class WriteImageFileException extends RuntimeException {
    public WriteImageFileException() {
    }

    public WriteImageFileException(String message) {
        super(message);
    }

    public WriteImageFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteImageFileException(Throwable cause) {
        super(cause);
    }
}
