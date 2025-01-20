package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.request.NewUserRequest;
import ru.practicum.shareit.user.request.UpdateUserRequest;

import java.util.HashMap;

@Slf4j
@Repository
public class UserStorage {
    private final HashMap<Long, User> users;

    public UserStorage() {
        users = new HashMap<>();
    }

    public UserDto create(NewUserRequest userRequest) {
        User user = UserMapper.mapToUser(userRequest);
        user.setId(idGeneration());
        users.put(user.getId(), user);
        log.info("Создаем нового пользователя с id {}", user.getId());

        return UserMapper.mapToUserDto(user);
    }

    public UserDto update(Long id, UpdateUserRequest updateUserRequest) {
        User updateUser = users.get(id);

        if (updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
            updateUser.setName(updateUserRequest.getName());
        }

        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            updateUser.setEmail(updateUserRequest.getEmail());
        }

        users.put(id, updateUser);
        log.info("Обновляем пользователя с id {}", id);

        return UserMapper.mapToUserDto(updateUser);
    }

    public UserDto get(Long id) {
        if (!userExists(id)) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        log.info("Отдаем пользователя с id {}", id);

        return UserMapper.mapToUserDto(users.get(id));
    }

    public void remove(Long id) {
        if (!userExists(id)) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        log.info("Удаляем пользователя с id {}", id);

        users.remove(id);
    }

    public boolean isEmailUnique(String email) {

        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    private Long idGeneration() {
        if (users.isEmpty()) {
            return 1L;
        } else {
            User us = users.get((long) users.size());
            return (us.getId() + 1L);
        }
    }

    public boolean userExists(Long id) {
        return users.containsKey(id);
    }
}
