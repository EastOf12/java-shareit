package ru.practicum.shareit.user;

import ru.practicum.shareit.user.request.NewUserRequest;
import ru.practicum.shareit.user.request.UpdateUserRequest;

public interface UserService {
    UserDto create(NewUserRequest newUserRequest);

    UserDto update(Long id, UpdateUserRequest updateUserRequest);

    UserDto get(Long id);

    void delete(Long id);
}
