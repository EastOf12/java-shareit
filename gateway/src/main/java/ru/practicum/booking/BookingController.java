package ru.practicum.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.booking.request.NewBookingRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @Valid @RequestBody NewBookingRequest newBookingRequest) {

        return bookingClient.create(userId, newBookingRequest);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> changeBookingStatus(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId,
            @RequestParam(defaultValue = "false") Boolean approved) {

        return bookingClient.changeBookingStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable Long bookingId) {

        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                                                      @RequestParam(defaultValue = "ALL") StateBooking state) {

        return bookingClient.getBookingsByBooker(bookerId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsByItemOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                         @RequestParam(defaultValue = "ALL") StateBooking state) {

        return bookingClient.getBookingsByItemOwner(ownerId, state);
    }
}
