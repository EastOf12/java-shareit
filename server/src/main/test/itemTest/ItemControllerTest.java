package itemTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController controller;
    private MockMvc mvc;

    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        itemDto = new ItemDto(
                1L,
                "боб",
                "вкусный боб",
                true,
                1L,
                null,
                null);
    }

    @Test
    void createItem_shouldReturnCreatedStatusAndItemDetails() throws Exception {
        Long owner = 1L;

        when(itemService.create(anyLong(), any()))
                .thenReturn(itemDto);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", owner)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.owner", is(itemDto.getOwner()), Long.class));
    }

    @Test
    void updateItem_shouldReturnCreatedStatusAndItemDetails() throws Exception {
        Long owner = 1L;

        when(itemService.update(anyLong(), anyLong(), any()))
                .thenReturn(itemDto);

        mvc.perform(patch("/items/{itemId}", itemDto.getId())
                        .header("X-Sharer-User-Id", owner)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.owner", is(itemDto.getOwner()), Long.class));
    }

    @Test
    void getItem_shouldReturnOkStatusAndItemDetails() throws Exception {
        Long owner = 1L;

        when(itemService.get(anyLong(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(get("/items/{itemId}", itemDto.getId())
                        .header("X-Sharer-User-Id", owner)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.owner", is(itemDto.getOwner()), Long.class));
    }

    @Test
    void getItems_shouldReturnOkStatusAndItemsDetails() throws Exception {
        Long userId = 1L;

        List<ItemDto> itemDtoList = List.of(itemDto);

        when(itemService.getAllUserItems(userId))
                .thenReturn(itemDtoList);

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemDto.getName())))
                .andExpect(jsonPath("$[0].description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$[0].owner", is(itemDto.getOwner()), Long.class));
    }

    @Test
    void searchItems_shouldReturnOkStatusAndItemsDetails() throws Exception {
        Long userId = 1L;
        String text = "боб";

        List<ItemDto> itemDtoList = List.of(itemDto);

        when(itemService.search(anyLong(), anyString()))
                .thenReturn(itemDtoList);

        mvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", userId)
                        .param("text", text)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemDto.getName())))
                .andExpect(jsonPath("$[0].description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$[0].owner", is(itemDto.getOwner()), Long.class));
    }

    @Test
    void createComment_shouldReturnCreateStatusAndCommentDetails() throws Exception {
        Long userId = 1L;

        CommentDto commentDto = new CommentDto(
                1L,
                "отличный боб",
                null,
                "любитель бобов",
                null
        );

        when(itemService.createComment(anyLong(), anyLong(), any()))
                .thenReturn(commentDto);

        mvc.perform(post("/items/{itemId}/comment", commentDto.getId())
                        .header("X-Sharer-User-Id", userId)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(commentDto.getText())))
                .andExpect(jsonPath("$.item", is(commentDto.getItem())))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.created", is(commentDto.getCreated())));
    }
}
