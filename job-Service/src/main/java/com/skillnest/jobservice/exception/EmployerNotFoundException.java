package com.skillnest.jobservice.exception;

public class EmployerNotFoundException extends RuntimeException {
    public EmployerNotFoundException(String message) {
        super(message);
    }
}
