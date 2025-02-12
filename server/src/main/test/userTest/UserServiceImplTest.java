package userTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.request.NewUserRequest;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest(
        classes = ShareItServer.class,
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {
    private final UserService service;

    @Test
    void testSaveUser() {
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setName("bob");
        newUserRequest.setEmail("bob@mail.ru");


        UserDto userDto = service.create(newUserRequest);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isGreaterThan(0);
        assertThat(userDto.getName()).isEqualTo("bob");
        assertThat(userDto.getEmail()).isEqualTo("bob@mail.ru");
    }

}