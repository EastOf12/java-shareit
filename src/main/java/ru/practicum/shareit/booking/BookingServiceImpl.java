package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.exeption.BookingNotValidException;
import ru.practicum.shareit.booking.request.NewBookingRequest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.exception.ItemNotAvailableException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto create(Long userId, NewBookingRequest newBookingRequest) {

        log.info("Создаем новый запрос бронирования от пользователя {}", userId);

        Item item = itemRepository.findById(newBookingRequest.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь " + newBookingRequest.getItemId() + " не найдена"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь " + userId + " не найден"));

        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Вещь с " + item.getId() + " недоступна для бронирования");
        }

        Booking booking = bookingRepository.save(
                BookingMapper.mapToNewBooking(newBookingRequest, item, user)
        );

        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public BookingDto changeBookingStatus(Long userId, Long bookingId, Boolean approved) {
        log.info("Пользователь {} меняет статус бронирования {} на {}", userId, bookingId, approved);

        if (userRepository.findById(userId).isEmpty()) {
            throw new BookingNotValidException("Создатель бронирования " + bookingId + " не корректен");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено"));

        if (!userId.equals(booking.getItem().getOwner())) {
            throw new BookingNotValidException("Пользователь " + userId + " не владелец вещи");
        }

        if (approved) {
            booking.setStatus(OrderStatus.APPROVED);
        } else {
            booking.setStatus(OrderStatus.REJECTED);
        }

        return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookingId) {
        log.info("Пользователь {} запросил информацию о бронировании {}", userId, bookingId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование " + bookingId + " не найдено"));


        if (!userId.equals(booking.getBooker().getId()) && !userId.equals(booking.getItem().getOwner())) {
            throw new BookingNotValidException("У пользователя " + userId + " нет доступа к бронированию " + bookingId);
        }

        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> getBookingsByBooker(Long bookerId, StateBooking state) {
        log.info("Пользователь {} запросил информацию о своих бронированиях", bookerId);

        if (userRepository.findById(bookerId).isEmpty()) {
            throw new NotFoundException("Пользователь " + bookerId + " не найден");
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "start");

        List<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.findByBooker_Id(bookerId, sort);
            case PAST -> bookingRepository.findByBooker_IdAndEndIsBefore(bookerId, LocalDateTime.now(), sort);
            case CURRENT -> bookingRepository.findByBooker_IdAndEndIsAfter(bookerId, LocalDateTime.now(), sort);
            case FUTURE -> bookingRepository.findByBooker_IdAndStartIsAfter(bookerId, LocalDateTime.now(), sort);
            case WAITING -> bookingRepository.findByBooker_IdAndStatus(bookerId, OrderStatus.WAITING, sort);
            case REJECTED -> bookingRepository.findByBooker_IdAndStatus(bookerId, OrderStatus.REJECTED, sort);
        };

        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(BookingMapper.mapToBookingDto(booking));
        }

        return bookingDtos;
    }

    @Override
    public List<BookingDto> getBookingsByItemOwner(Long ownerId, StateBooking state) {
        log.info("Пользователь {} запросил информацию о бронированиях своих вещей", ownerId);

        if (userRepository.findById(ownerId).isEmpty()) {
            throw new NotFoundException("Пользователь " + ownerId + " не найден");
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "start");

        List<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.findByItem_Id(ownerId, sort);
            case PAST -> bookingRepository.findByItem_IdAndEndIsBefore(ownerId, LocalDateTime.now(), sort);
            case CURRENT -> bookingRepository.findByItem_IdAndEndIsAfter(ownerId, LocalDateTime.now(), sort);
            case FUTURE -> bookingRepository.findByItem_IdAndStartIsAfter(ownerId, LocalDateTime.now(), sort);
            case WAITING -> bookingRepository.findByItem_IdAndStatus(ownerId, OrderStatus.WAITING, sort);
            case REJECTED -> bookingRepository.findByItem_IdAndStatus(ownerId, OrderStatus.REJECTED, sort);
        };

        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(BookingMapper.mapToBookingDto(booking));
        }

        return bookingDtos;
    }
}
