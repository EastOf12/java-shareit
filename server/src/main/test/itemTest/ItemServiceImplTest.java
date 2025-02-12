package itemTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.service.ItemService;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest(
        classes = ShareItServer.class,
        properties = "jdbc.url=jdbc:postgresql://localhost:5432/test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemServiceImplTest {
    private final ItemService itemService;

    @Test
    void testSaveItem() {
        Long userId = 1L;
        NewItemRequest newItemRequest = new NewItemRequest();
        newItemRequest.setName("боб");
        newItemRequest.setDescription("Классный боб");
        newItemRequest.setAvailable(true);


        ItemDto itemDto = itemService.create(userId, newItemRequest);

        assertThat(itemDto).isNotNull();
        assertThat(itemDto.getId()).isGreaterThan(0);
        assertThat(itemDto.getOwner()).isEqualTo(userId);
        assertThat(itemDto.getName()).isEqualTo("боб");
        assertThat(itemDto.getDescription()).isEqualTo("Классный боб");
        assertThat(itemDto.getAvailable()).isEqualTo(true);
    }

}