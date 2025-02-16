package itemRequestTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Mock
    private ItemRequestService itemRequestService;
    @InjectMocks
    private ItemRequestController controller;
    ;
    private MockMvc mvc;

    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        itemRequestDto = new ItemRequestDto(
                1L,
                "Хочу коньки",
                null,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void createItemRequest_shouldReturnCreatedStatusAndItemRequestDetails() throws Exception {
        NewItemRequest newItemRequest = new NewItemRequest(
                "Хочу коньки"
        );

        Long userId = 1L;

        when(itemRequestService.create(anyLong(), any()))
                .thenReturn(itemRequestDto);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", userId)
                        .content(mapper.writeValueAsString(newItemRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(itemRequestDto.getId()), Long.class));
    }

    @Test
    void getUserItemRequest_shouldReturnOkStatusAndItemRequestDetails() throws Exception {
        Long userId = 1L;
        List<ItemRequestDto> itemRequestDtos = List.of(itemRequestDto);

        when(itemRequestService.getUserItemRequest(anyLong()))
                .thenReturn(itemRequestDtos);

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemRequestDto.getId()), Long.class));
    }

    @Test
    void getItemRequest_shouldReturnOkStatusAndItemRequestDetails() throws Exception {
        Long userId = 1L;

        when(itemRequestService.getItemRequest(anyLong(), anyLong()))
                .thenReturn(itemRequestDto);

        mvc.perform(get("/requests/{requestId}", 1L)
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequestDto.getId()), Long.class));
    }

    @Test
    void getItemRequestAll_shouldReturnOkStatusAndItemRequestDetails() throws Exception {
        Long userId = 1L;
        List<ItemRequestDto> itemRequestDtos = List.of(itemRequestDto);

        when(itemRequestService.getItemRequestAll(anyLong()))
                .thenReturn(itemRequestDtos);

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemRequestDto.getId()), Long.class));
    }
}