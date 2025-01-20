package ru.practicum.shareit.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice
public class ItemExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotOwnerItemException(final UserNotOwnerItemException e) {
        return Map.of(
                "error", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleItemNotExistException(final ItemNotExistException e) {
        return Map.of(
                "error", e.getMessage()
        );
    }


}