package com.example.petmanagement.exception;

public class HouseholdNotFoundException extends RuntimeException {
    public HouseholdNotFoundException(String message) {
        super(message);
    }
}
