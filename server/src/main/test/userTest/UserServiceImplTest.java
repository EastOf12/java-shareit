package userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.request.NewUserRequest;
import ru.practicum.shareit.user.request.UpdateUserRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    User user;
    UserDto userDto;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("bob");
        user.setEmail("bob@example.com");

        userDto = new UserDto(
                1L,
                "bob",
                "bob@example.com"
        );
    }

    @Test
    void createUser_whenValidRequest_thenReturnsUserDto() throws Exception {
        when(userRepository.save(any()))
                .thenReturn(user);

        when(userRepository.findByEmailContainingIgnoreCase(any()))
                .thenReturn(new ArrayList<>());

        NewUserRequest newUserRequest = new NewUserRequest();
        UserDto userDtoNew = userService.create(newUserRequest);

        assertEquals(userDto, userDtoNew, "Созданный пользователь не совпадает с ожидаемым");
    }

    @Test
    void updateUser_whenValidRequest_thenReturnsUserDto() throws Exception {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        when(userRepository.findByEmailContainingIgnoreCase(any()))
                .thenReturn(new ArrayList<>());

        when(userRepository.save(any()))
                .thenReturn(user);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        UserDto userDtoUpdate = userService.update(1L, updateUserRequest);

        assertEquals(userDto, userDtoUpdate, "Обновленный пользователь пользователь не совпадает с ожидаемым");
    }

    @Test
    void getUser_whenValidRequest_thenReturnsUserDto() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        UserDto userDto = userService.get(1L);

        assertEquals(userDto, userDto, "Полученный пользователь пользователь не совпадает с ожидаемым");
    }

    @Test
    void deleteUser_whenValidRequest_thenReturnsUserDto() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        userService.delete(1L);

        verify(userRepository, times(1)).delete(user);
    }
}
