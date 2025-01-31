package ru.practicum.shareit.booking.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewBookingRequest {

    @NotNull
    private Long itemId;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    @AssertTrue(message = "Start не может быть в прошлом")
    public boolean isStartNotInPast() {
        if (start == null) {
            return false;
        }

        return start.isAfter(LocalDateTime.now());
    }

    @AssertTrue(message = "End не можем быть в прошлом")
    public boolean isEndNotInPast() {
        if (end == null) {
            return false;
        }

        return end.isAfter(LocalDateTime.now());
    }

    @AssertTrue(message = "Start и end не могут быть равны")
    public boolean isStartEndNotEqual() {
        if (end == null || start == null) {
            return false;
        }

        return !start.isEqual(end);
    }

    @AssertTrue(message = "End не может быть после Start")
    public boolean isEndAfterStart() {
        if (end == null || start == null) {
            return false;
        }

        return end.isAfter(start);
    }
}


