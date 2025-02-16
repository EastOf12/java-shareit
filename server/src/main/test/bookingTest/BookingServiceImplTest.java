package bookingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.request.NewBookingRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    Item item;
    User user;
    Booking booking;
    BookingDto bookingDto;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("bob");
        user.setEmail("bob@example.com");

        item = new Item();
        item.setId(1L);
        item.setName("Боб");
        item.setDescription("Очень вкусный боб");
        item.setAvailable(true);
        item.setOwner(user);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        booking = new Booking(
                1L,
                start,
                end,
                item,
                user,
                OrderStatus.WAITING
        );

        bookingDto = new BookingDto(
                1L,
                start,
                end,
                item,
                user,
                OrderStatus.WAITING
        );
    }

    @Test
    void createBooking_whenValidRequest_thenReturnsBookingDto() throws Exception {
        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(item));

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(bookingRepository.findByItem_Id(any(), any()))
                .thenReturn(new ArrayList<>());

        when(bookingRepository.save(any()))
                .thenReturn(booking);

        NewBookingRequest newBookingRequest = new NewBookingRequest(
                item.getId(),
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(6)
        );

        BookingDto bookingDtoNew = bookingService.create(user.getId(), newBookingRequest);

        assertEquals(bookingDto, bookingDtoNew, "Полученное бронирование не совпадает с ожидаемым");
    }

    @Test
    void changeBookingStatus_whenValidRequest_thenReturnsBookingDto() throws Exception {
        bookingDto.setStatus(OrderStatus.APPROVED);

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(bookingRepository.findById(any()))
                .thenReturn(Optional.ofNullable(booking));

        when(bookingRepository.save(any()))
                .thenReturn(booking);

        BookingDto bookingDtoNew = bookingService.changeBookingStatus(user.getId(), booking.getId(), true);

        assertEquals(bookingDto, bookingDtoNew, "Полученное бронирование не совпадает с ожидаемым");
    }

    @Test
    void getBooking_whenValidRequest_thenReturnsBookingDto() throws Exception {
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(bookingRepository.findById(any()))
                .thenReturn(Optional.ofNullable(booking));

        BookingDto bookingDtoNew = bookingService.getBooking(user.getId(), booking.getId());

        assertEquals(bookingDto, bookingDtoNew, "Полученное бронирование не совпадает с ожидаемым");
    }

    @Test
    void getBookingsByBooker_whenValidRequest_thenReturnsBookingDtos() throws Exception {
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(bookingRepository.findByBooker_Id(any(), any()))
                .thenReturn(new ArrayList<>(List.of(booking)));

        List<BookingDto> bookingDtos = bookingService.getBookingsByBooker(user.getId(), StateBooking.ALL);

        assertEquals(bookingDto, bookingDtos.getFirst(), "Полученное бронирование не совпадает с ожидаемым");
    }

    @Test
    void getBookingsByItemOwner_whenValidRequest_thenReturnsBookingDtos() throws Exception {
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(bookingRepository.findByItem_Id(any(), any()))
                .thenReturn(new ArrayList<>(List.of(booking)));

        List<BookingDto> bookingDtos = bookingService.getBookingsByItemOwner(user.getId(), StateBooking.ALL);

        assertEquals(bookingDto, bookingDtos.getFirst(), "Полученное бронирование не совпадает с ожидаемым");
    }

}
