package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.exception.EmailAlreadyExistsException;
import ru.practicum.shareit.user.request.NewUserRequest;
import ru.practicum.shareit.user.request.UpdateUserRequest;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        log.info("Создаем нового пользователя");

        if (!userRepository.findByEmailContainingIgnoreCase(newUserRequest.getEmail()).isEmpty()) {
            throw new EmailAlreadyExistsException("Email уже используется");
        }

        User user = userRepository.save(UserMapper.mapToUser(newUserRequest));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto update(Long id, UpdateUserRequest updateUserRequest) {
        log.info("Обновляем пользователя с id {}", id);

        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));

        if (!userRepository.findByEmailContainingIgnoreCase(updateUserRequest.getEmail()).isEmpty()) {
            throw new EmailAlreadyExistsException("Email уже используется");
        }

        if (updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
            updateUser.setName(updateUserRequest.getName());
        }

        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            updateUser.setEmail(updateUserRequest.getEmail());
        }

        User user = userRepository.save(updateUser);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto get(Long id) {
        log.info("Отдаем пользователя с id {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Удаляем пользователя с id {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));

        userRepository.delete(user);
    }
}