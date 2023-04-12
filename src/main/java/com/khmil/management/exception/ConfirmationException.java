package com.khmil.management.exception;

public class ConfirmationException extends RuntimeException {
    public ConfirmationException(Long id) {
        super(String.format("Session with id = %s is not confirmed for removing", id));
    }
}
