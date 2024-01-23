package com.epam.exceptions;

public class OperationFailureException extends RuntimeException {
    public OperationFailureException(String message) {
        super(message);
    }
}