package com.oracle.training.exception;

public class InterpreterException extends Exception {
    public InterpreterException() {
    }

    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(String message, Exception cause) {
        super(message, cause);
    }

    public InterpreterException(Exception cause) {
        super(cause);
    }
}
