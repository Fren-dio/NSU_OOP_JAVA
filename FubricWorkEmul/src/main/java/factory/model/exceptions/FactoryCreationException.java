package factory.model.exceptions;

public class FactoryCreationException extends Exception {
    public FactoryCreationException() {
        super();
    }

    public FactoryCreationException(String message) {
        super(message);
    }

    public FactoryCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
