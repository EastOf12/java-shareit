package ru.practicum.shareit.user.exception;

public class EmailCorrectException extends RuntimeException {
    public EmailCorrectException(String message) {
        super(message);
    }
}