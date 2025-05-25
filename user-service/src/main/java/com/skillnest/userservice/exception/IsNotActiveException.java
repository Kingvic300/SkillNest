package com.skillnest.userservice.exception;

public class IsNotActiveException extends RuntimeException {
    public IsNotActiveException(String message) {
        super(message);
    }
}
