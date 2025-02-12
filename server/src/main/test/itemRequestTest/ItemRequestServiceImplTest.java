package itemRequestTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest(
        classes = ShareItServer.class,
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestServiceImplTest {
    private final ItemRequestService itemRequestService;

    @Test
    void testSaveItemRequest() {
        Long userId = 1L;
        NewItemRequest newItemRequest = new NewItemRequest();
        newItemRequest.setDescription("Хочу этот классный боб");

        ItemRequestDto itemRequestDto = itemRequestService.create(userId, newItemRequest);

        assertThat(itemRequestDto).isNotNull();
        assertThat(itemRequestDto.getId()).isGreaterThan(0);
        assertThat(itemRequestDto.getOwner().getId()).isEqualTo(userId);
        assertThat(itemRequestDto.getCreated()).isNotNull();
        assertThat(itemRequestDto.getDescription()).isEqualTo("Хочу этот классный боб");
    }

}
