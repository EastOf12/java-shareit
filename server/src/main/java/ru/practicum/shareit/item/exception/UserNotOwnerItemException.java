package ru.practicum.shareit.item.exception;

public class UserNotOwnerItemException extends RuntimeException {
    public UserNotOwnerItemException(String message) {
        super(message);
    }
}
