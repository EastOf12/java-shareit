package itemRequestTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.ItemRequestServiceImpl;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceImplTest {
    Item item;
    User user;
    ItemRequestDto itemRequestDto;
    ItemRequest itemRequest;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRequestRepository itemRequestRepository;
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("bob");
        user.setEmail("bob@example.com");

        item = new Item();
        item.setId(1L);
        item.setName("Боб");
        item.setDescription("Очень вкусный боб");
        item.setAvailable(true);
        item.setOwner(user);

        LocalDateTime createdDate = LocalDateTime.now().minusDays(2);

        itemRequestDto = new ItemRequestDto(
                1L,
                "Хочу найти вкусный боб",
                user,
                createdDate,
                new ArrayList<>()
        );


        itemRequest = new ItemRequest(
                1L,
                "Хочу найти вкусный боб",
                user,
                createdDate
        );
    }

    @Test
    void createItemRequest_whenValidRequest_thenReturnsItemRequestDto() throws Exception {
        when(itemRequestRepository.save(any()))
                .thenReturn(itemRequest);

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        NewItemRequest newItemRequest = new NewItemRequest();

        ItemRequestDto itemRequestDtoNew = itemRequestService.create(user.getId(), newItemRequest);

        assertEquals(itemRequestDto, itemRequestDtoNew, "Полученный запрос не совпадает с ожидаемым");
    }

    @Test
    void getUserItemRequest_whenValidRequest_thenReturnsItemRequestDtos() throws Exception {
        when(itemRequestRepository.findByOwnerId(any()))
                .thenReturn(List.of(itemRequest));

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        List<ItemRequestDto> itemRequestsDtos = itemRequestService.getUserItemRequest(user.getId());

        assertEquals(itemRequestDto, itemRequestsDtos.getFirst(), "Полученный запрос не совпадает с ожидаемым");
    }

    @Test
    void getItemRequest_whenValidRequest_thenReturnsItemRequestDto() throws Exception {
        itemRequestDto.setItems(List.of(item));

        when(itemRequestRepository.findById(any()))
                .thenReturn(Optional.ofNullable(itemRequest));

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(itemRepository.findByRequest(any()))
                .thenReturn(List.of(item));

        ItemRequestDto itemRequestsDtoNew = itemRequestService.getItemRequest(user.getId(), itemRequest.getId());

        assertEquals(itemRequestDto, itemRequestsDtoNew, "Полученный запрос не совпадает с ожидаемым");
    }

    @Test
    void getItemRequestAll_whenValidRequest_thenReturnsItemRequestDtos() throws Exception {
        item.setRequest(1L);
        itemRequestDto.setItems(List.of(item));

        when(itemRequestRepository.findByOwnerIdNot(any()))
                .thenReturn(List.of(itemRequest));

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));

        when(itemRepository.findByRequests(any()))
                .thenReturn(List.of(item));

        List<ItemRequestDto> itemRequestsDtos = itemRequestService.getItemRequestAll(user.getId());

        assertEquals(itemRequestDto, itemRequestsDtos.getFirst(), "Полученный запрос не совпадает с ожидаемым");
    }
}
