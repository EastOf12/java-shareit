package ru.practicum.user.email;

public class EmailCorrectException extends RuntimeException {
    public EmailCorrectException(String message) {
        super(message);
    }
}