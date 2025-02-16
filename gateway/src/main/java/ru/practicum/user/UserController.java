package ru.practicum.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.request.NewUserRequest;
import ru.practicum.user.request.UpdateUserRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient userClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody NewUserRequest newUserRequest) {
        return userClient.create(newUserRequest);
    } //Создать пользователя


    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userClient.update(id, updateUserRequest);
    } //Обновить пользователя

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return userClient.getUser(id);
    } //Получить пользователя

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userClient.deleteUser(id);
    } //Удалить пользователя
}
