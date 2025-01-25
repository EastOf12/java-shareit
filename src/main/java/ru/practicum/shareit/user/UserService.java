package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.request.NewUserRequest;
import ru.practicum.shareit.user.request.UpdateUserRequest;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserDto create(NewUserRequest newUserRequest) {
        return userStorage.create(newUserRequest);
    }

    public UserDto update(Long id, UpdateUserRequest updateUserRequest) {
        return userStorage.update(id, updateUserRequest);
    }

    public UserDto get(Long id) {
        return userStorage.get(id);
    }

    public void delete(Long id) {
        userStorage.remove(id);
    }
}
