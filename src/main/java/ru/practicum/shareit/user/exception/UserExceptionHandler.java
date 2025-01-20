package ru.practicum.shareit.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("email", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmailCorrectException.class)
    public ResponseEntity<Map<String, String>> handleEmailCorrectException(EmailCorrectException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("email", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmailCorrectException(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("email", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}