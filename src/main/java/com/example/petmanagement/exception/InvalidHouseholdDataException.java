package com.example.petmanagement.exception;

public class InvalidHouseholdDataException extends RuntimeException {
    public InvalidHouseholdDataException(String message) {
        super(message);
    }
}
