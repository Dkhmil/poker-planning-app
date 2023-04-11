package com.khmil.management.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super(String.format("Resource with id = %s is not found", id));
    }
}
