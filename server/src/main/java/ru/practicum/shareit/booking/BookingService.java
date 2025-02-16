package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.request.NewBookingRequest;

import java.util.List;

public interface BookingService {
    BookingDto create(Long userId, NewBookingRequest newBookingRequest);

    BookingDto changeBookingStatus(Long userId, Long bookingId, Boolean approved);

    BookingDto getBooking(Long userId, Long bookingId);

    List<BookingDto> getBookingsByBooker(Long bookerId, StateBooking state);

    List<BookingDto> getBookingsByItemOwner(Long owner, StateBooking state);
}
