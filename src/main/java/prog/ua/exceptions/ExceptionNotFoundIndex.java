package prog.ua.exceptions;

public class ExceptionNotFoundIndex extends Exception{
    private String message;

    public ExceptionNotFoundIndex(String message) {
        super(message);
    }
}
