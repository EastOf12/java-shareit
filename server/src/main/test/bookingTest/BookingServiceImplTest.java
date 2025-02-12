package bookingTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.request.NewBookingRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest(
        classes = ShareItServer.class,
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingServiceImplTest {
    private final BookingService bookingService;
    private final ItemService itemService;

    @Test
    void testSaveBookingRequest() {
        Long userId = 1L;
        ru.practicum.shareit.item.request.NewItemRequest newItemRequest = new NewItemRequest();
        newItemRequest.setName("боб");
        newItemRequest.setDescription("Классный боб");
        newItemRequest.setAvailable(true);

        ItemDto itemDto = itemService.create(userId, newItemRequest);

        NewBookingRequest newBookingRequest = new NewBookingRequest(
                itemDto.getId(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2)
        );

        BookingDto bookingDto = bookingService.create(userId, newBookingRequest);

        assertThat(bookingDto).isNotNull();
        assertThat(bookingDto.getId()).isGreaterThan(0);
        assertThat(bookingDto.getBooker().getId()).isEqualTo(userId);
    }

}
