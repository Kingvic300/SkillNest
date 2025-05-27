//package com.skillnest.userservice.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(AlreadyExistsException.class)
//    public ResponseEntity<AlreadyExistsException> alreadyExistsException(AlreadyExistsException e) {
//        return new ResponseEntity<>(e, HttpStatus.CONFLICT);
//    }
//    @ExceptionHandler(EmailNotSentException.class)
//    public ResponseEntity<EmailNotSentException> emailNotSentException(EmailNotSentException e) {
//        return new ResponseEntity<>(e, HttpStatus.CONFLICT);
//    }
//    @ExceptionHandler(InvalidEmailException.class)
//    public ResponseEntity<InvalidEmailException> invalidEmailException(InvalidEmailException e) {
//        return new ResponseEntity<>(e, HttpStatus.IM_USED);
//    }
//    @ExceptionHandler(InvalidOtpException.class)
//    public ResponseEntity<InvalidOtpException> invalidOtpException(InvalidOtpException e) {
//        return new ResponseEntity<>(e, HttpStatus.CONFLICT);
//    }
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<UsernameNotFoundException> usernameNotFoundException(UsernameNotFoundException e) {
//        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//    }
//
//    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", status.value());
//        body.put("error", status.getReasonPhrase());
//        body.put("message", message);
//        return new ResponseEntity<>(body, status);
//    }
//}
