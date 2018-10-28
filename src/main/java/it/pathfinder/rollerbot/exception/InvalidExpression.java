package it.pathfinder.rollerbot.exception;

public class InvalidExpression extends RuntimeException {

    public InvalidExpression() {
        super();
    }

    public InvalidExpression(String message) {
        super(message);
    }

    public InvalidExpression(String message, Throwable throwable) {
        super(message, throwable);
    }

}
