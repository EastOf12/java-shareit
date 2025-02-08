package ru.practicum.shareit.booking.exeption;

public class BookingNotValidException extends RuntimeException {
    public BookingNotValidException(String message) {
        super(message);
    }
}
