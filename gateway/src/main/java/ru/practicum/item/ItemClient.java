package ru.practicum.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.BaseClient;
import ru.practicum.item.request.NewCommentRequest;
import ru.practicum.item.request.NewItemRequest;
import ru.practicum.item.request.UpdateItemRequest;
import java.util.Map;

@Service
@Slf4j
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {

        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> create(Long owner, NewItemRequest newItemRequest) {
        log.info("Создаем вещь");
        return post("", owner, newItemRequest);
    }

    public ResponseEntity<Object> update(Long userId, Long itemId, UpdateItemRequest updateItemRequest) {
        log.info("Обновляем вещь {}", itemId);
        return patch("/" + itemId, userId, updateItemRequest);
    }

    public ResponseEntity<Object> getItem(Long userId, Long itemId) {
        log.info("Предаем информацию о вещи {} пользователю {}", itemId, userId);
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getAllUserItems(Long userId) {
        log.info("Предаем информацию о вещах пользователя {}", userId);
        return get("", userId);
    }

    public ResponseEntity<Object> search(Long userId, String text) {
        log.info("Производим поиск вещи пользователем {} по запросу - {}", userId, text);
        Map<String, Object> parameters = Map.of(
                "text", text
        );

        return get("/search?text={text}", userId, parameters);
    }

    public ResponseEntity<Object> createComment(Long userId, Long itemId, NewCommentRequest newCommentRequest) {
        log.info("Пользователь {} создает комментарий на вещь {}", userId, itemId);

        String path = "/" + itemId + "/comment";

        return post(path, userId, newCommentRequest);
    }
}
