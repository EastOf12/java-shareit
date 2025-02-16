package bookingTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.OrderStatus;
import ru.practicum.shareit.booking.StateBooking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.request.NewBookingRequest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class BookingControllerTest {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Mock
    private BookingService bookingService;
    @InjectMocks
    private BookingController controller;
    ;
    private MockMvc mvc;

    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        bookingDto = new BookingDto(
                1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                null,
                null,
                OrderStatus.APPROVED);
    }

    @Test
    void createBooking_shouldReturnCreatedStatusAndBookingDetails() throws Exception {
        NewBookingRequest newBookingRequest = new NewBookingRequest(
                1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2)
        );


        Long userId = 1L;

        when(bookingService.create(anyLong(), any()))
                .thenReturn(bookingDto);

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", userId)
                        .content(mapper.writeValueAsString(newBookingRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void changeBookingStatus_shouldReturnOkStatus() throws Exception {
        Boolean approved = true;

        Long userId = 1L;

        when(bookingService.changeBookingStatus(anyLong(), anyLong(), any()))
                .thenReturn(bookingDto);

        mvc.perform(patch("/bookings/{bookingId}", 1L)
                        .header("X-Sharer-User-Id", userId)
                        .param("approved", String.valueOf(approved))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class));
    }

    @Test
    void getBooking_shouldReturnOkStatusAndBookingDetails() throws Exception {
        Long userId = 1L;

        when(bookingService.getBooking(anyLong(), anyLong()))
                .thenReturn(bookingDto);

        mvc.perform(get("/bookings/{bookingId}", 1L)
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class));
    }

    @Test
    void getBookingsByBooker_shouldReturnOkStatusAndBookingDetails() throws Exception {
        Long userId = 1L;
        List<BookingDto> bookingDtos = List.of(bookingDto);

        when(bookingService.getBookingsByBooker(anyLong(), any()))
                .thenReturn(bookingDtos);

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", userId)
                        .param("state", String.valueOf(StateBooking.WAITING))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class));
    }

    @Test
    void getBookingsByItemOwner_shouldReturnOkStatusAndBookingDetails() throws Exception {
        Long userId = 1L;
        List<BookingDto> bookingDtos = List.of(bookingDto);

        when(bookingService.getBookingsByItemOwner(anyLong(), any()))
                .thenReturn(bookingDtos);

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", userId)
                        .param("state", String.valueOf(StateBooking.WAITING))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class));
    }

}
