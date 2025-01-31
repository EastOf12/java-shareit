package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.request.NewBookingRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody NewBookingRequest newBookingRequest) {

        return bookingService.create(userId, newBookingRequest);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto changeBookingStatus(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId,
            @RequestParam Boolean approved) {

        return bookingService.changeBookingStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable Long bookingId) {

        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                                                @RequestParam(defaultValue = "ALL") StateBooking state) {

        return bookingService.getBookingsByBooker(bookerId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsByItemOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                   @RequestParam(defaultValue = "ALL") StateBooking state) {

        return bookingService.getBookingsByItemOwner(ownerId, state);
    }
}
