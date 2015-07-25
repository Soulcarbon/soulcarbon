package ru.sw.platform.core.exceptions;

public class PlatofrmExecption extends RuntimeException {

    public enum Type {
        ReadError,
        CreateError,
        UpdateError,
        CountError,
        RemoveError,
        ListError,
        ListPartialError,
        ActionError,
        AutheneticationError
    }

    private Type type;

    private String message;


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public PlatofrmExecption(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
