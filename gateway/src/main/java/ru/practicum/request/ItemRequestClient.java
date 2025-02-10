package ru.practicum.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.BaseClient;

@Service
@Slf4j
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {

        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> create(Long owner, NewItemRequest newItemRequest) {
        log.info("Создаем запрос на вещь");

        return post("", owner, newItemRequest);
    }

    public ResponseEntity<Object> getUserItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получаем все запросы на вещь пользователя {}", userId);

        return get("", userId);
    }

    public ResponseEntity<Object> getItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("Возвращаем запрос {} пользователю {}", requestId, userId);

        return get("/" + requestId, userId);
    }

    public ResponseEntity<Object> getItemRequestAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Возвращаем все запросы других пользователей пользователю {}", userId);

        return get("/all", userId);
    }

}
