package ru.mkonovalov.jurdoc.core.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
