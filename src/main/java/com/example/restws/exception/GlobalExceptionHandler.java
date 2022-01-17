package com.example.restws.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(UserNotFoundException ex) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(HttpStatus.NOT_FOUND.value());
        details.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(details);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExists(UserAlreadyExistsException ex) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(HttpStatus.CONFLICT.value());
        details.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(details);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleResourceAlreadyExists(AuthenticationException ex) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(HttpStatus.FORBIDDEN.value());
        details.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(details);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(status.value());
        details.setMessage(status.getReasonPhrase());

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            details.addErrors(fieldName, errorMessage);
        });

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(details);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(status.value());
        details.setMessage("could not parse Json");

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(details);
    }

    @Override   // catch any other exception for standard error message handling
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(status.value());
        details.setMessage("internal server error");

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(details);
    }
}
