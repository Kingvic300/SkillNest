package com.skillnest.jobSeekerService.exception;

public class NoBankFoundException extends RuntimeException {
    public NoBankFoundException(String message) {
        super(message);
    }
}
