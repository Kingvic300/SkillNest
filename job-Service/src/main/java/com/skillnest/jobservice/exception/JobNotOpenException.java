package com.skillnest.jobservice.exception;

public class JobNotOpenException extends RuntimeException {
    public JobNotOpenException(String message) {
        super(message);
    }
}
