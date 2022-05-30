package ru.mkonovalov.jurdoc.core.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> clazz) {
        super(NotFoundException.class.getSimpleName());
    }
    public NotFoundException(Class<?> clazz, Long id) {
        super(NotFoundException.class.getSimpleName() + ": " + clazz.getSimpleName() + " with id: " + id);
    }

    public NotFoundException(Class<?> clazz, String data) {
        super(NotFoundException.class.getSimpleName() + ": " + clazz.getSimpleName() + " - data: " + data);
    }
}
