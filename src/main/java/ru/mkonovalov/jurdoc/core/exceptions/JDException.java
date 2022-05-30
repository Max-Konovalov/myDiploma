package ru.mkonovalov.jurdoc.core.exceptions;

public class JDException extends RuntimeException{
    public JDException() {
    }

    public JDException(String message) {
        super(message);
    }
}
