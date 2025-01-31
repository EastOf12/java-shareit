package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.request.NewBookingRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookingMapper {

    public static Booking mapToNewBooking(NewBookingRequest newBookingRequest, Item item, User booker) {

        return new Booking(
                newBookingRequest.getStart(),
                newBookingRequest.getEnd(),
                item,
                booker,
                OrderStatus.WAITING
        );
    }

    public static BookingDto mapToBookingDto(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus()
        );
    }
}
