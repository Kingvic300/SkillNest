package com.skillnest.jobSeekerService.exception;

public class NoDocumentFoundException extends RuntimeException {
    public NoDocumentFoundException(String message) {
        super(message);
    }
}
