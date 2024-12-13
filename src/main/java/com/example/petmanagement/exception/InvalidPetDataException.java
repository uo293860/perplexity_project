package com.example.petmanagement.exception;

public class InvalidPetDataException extends RuntimeException {
    public InvalidPetDataException(String message) {
        super(message);
    }
}
