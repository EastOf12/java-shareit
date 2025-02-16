package ru.practicum.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.BaseClient;
import ru.practicum.user.request.NewUserRequest;
import ru.practicum.user.request.UpdateUserRequest;

@Service
@Slf4j
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {

        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> create(NewUserRequest newUserRequest) {
        log.info("Создаем нового пользователя");
        return post("", newUserRequest);
    }

    public ResponseEntity<Object> update(Long id, UpdateUserRequest updateUserRequest) {
        log.info("Обновляем пользователя с id {}", id);
        return patch("/" + id, updateUserRequest);
    }

    public ResponseEntity<Object> getUser(Long id) {
        log.info("Отдаем пользователя с id {}", id);
        return get("/" + id, id);
    }

    public void deleteUser(Long id) {
        log.info("Удаляем пользователя с id {}", id);
        delete("/" + id, id);
    }
}
