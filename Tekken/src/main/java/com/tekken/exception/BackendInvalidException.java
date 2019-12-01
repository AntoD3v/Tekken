package com.tekken.exception;

public class BackendInvalidException extends Throwable {

    public BackendInvalidException(String clazz) {
        super("Clazz backends not found ("+clazz+")");
    }
}
