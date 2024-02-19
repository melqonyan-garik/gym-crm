package com.epam.config;

import com.epam.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserConflictException.class)
    public ResponseEntity<String> handleUserConflictException(UserConflictException e) {
        log.error("User conflict error", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException e) {
        log.error("Invalid date format error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'dd.MM.yyyy'.");
    }
    @ExceptionHandler(InvalidSpecializationException.class)
    public ResponseEntity<String> handleInvalidSpecializationException(InvalidSpecializationException e) {
        log.error("Invalid Specialization", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation error", e);
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException e) {
        log.error("Wrong password error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password: " + e.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException e) {
        log.error("invalid password error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password: " + e.getMessage());
    }

    @ExceptionHandler(OperationFailureException.class)
    public ResponseEntity<String> handleOperationFailureException(OperationFailureException e) {
        log.error("Operation failed", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An operation failed: " + e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String message = "An error occurred";
        log.error(message, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }
}
