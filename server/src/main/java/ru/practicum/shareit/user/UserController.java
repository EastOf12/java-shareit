package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.request.NewUserRequest;
import ru.practicum.shareit.user.request.UpdateUserRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody NewUserRequest newUserRequest) {
        return userService.create(newUserRequest);
    } //Создать пользователя

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.update(id, updateUserRequest);
    } //Обновить пользователя

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    } //Получить пользователя

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    } //Удалить пользователя
}
