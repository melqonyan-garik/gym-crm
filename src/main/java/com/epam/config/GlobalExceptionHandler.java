package com.epam.config;

import com.epam.exceptions.InvalidPasswordException;
import com.epam.exceptions.OperationFailureException;
import com.epam.exceptions.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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
