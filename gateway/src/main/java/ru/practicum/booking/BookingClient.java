package ru.practicum.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.BaseClient;
import ru.practicum.booking.request.NewBookingRequest;

import java.util.Map;

@Slf4j
@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {

        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> create(Long userId, NewBookingRequest newBookingRequest) {
        log.info("Создаем новый запрос бронирования от пользователя {}", userId);
        return post("", userId, newBookingRequest);
    }

    public ResponseEntity<Object> changeBookingStatus(Long userId, Long bookingId, Boolean approved) {
        log.info("Пользователь {} меняет статус бронирования {} на {}", userId, bookingId, approved);
        return patch("/" + bookingId + "?approved=" + approved, (long) userId);
    }

    public ResponseEntity<Object> getBooking(Long userId, Long bookingId) {
        log.info("Пользователь {} запросил информацию о бронировании {}", userId, bookingId);
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getBookingsByBooker(Long bookerId, StateBooking state) {
        log.info("Пользователь {} запросил информацию о своих бронированиях", bookerId);
        Map<String, Object> parameters = Map.of(
                "state", state
        );

        return get("", bookerId, parameters);
    }

    public ResponseEntity<Object> getBookingsByItemOwner(Long ownerId, StateBooking state) {
        log.info("Пользователь {} запросил информацию о бронированиях своих вещей", ownerId);
        Map<String, Object> parameters = Map.of(
                "state", state
        );

        return get("/owner", ownerId, parameters);
    }
}
