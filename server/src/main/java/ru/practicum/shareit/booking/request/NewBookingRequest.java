package ru.practicum.shareit.booking.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewBookingRequest {

    @NotNull
    private Long itemId;

    @NotNull
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull
    @Future
    private LocalDateTime end;

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


