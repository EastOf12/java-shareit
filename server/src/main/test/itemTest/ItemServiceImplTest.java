package itemTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.OrderStatus;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.request.NewCommentRequest;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.request.UpdateItemRequest;
import ru.practicum.shareit.item.service.ItemServiceImpl;
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
public class ItemServiceImplTest {
    Item item;
    User user;
    ItemDto itemDto;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private ItemServiceImpl itemService;

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

        itemDto = new ItemDto(
                1L,
                "Боб",
                "Очень вкусный боб",
                true,
                1L,
                null
        );
    }

    @Test
    void createItem_whenValidRequest_thenReturnsItemDto() throws Exception {
        when(itemRepository.save(any()))
                .thenReturn(item);

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new User()));

        ItemDto itemDtoNew = itemService.create(user.getId(), new NewItemRequest());

        assertEquals(itemDto, itemDtoNew, "Созданная вещь не совпадает с ожидаемой");
    }

    @Test
    void updateItem_whenValidRequest_thenReturnsItemDto() throws Exception {
        when(itemRepository.save(any()))
                .thenReturn(item);

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new User()));

        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(item));

        ItemDto itemDtoUpdate = itemService.update(user.getId(), item.getId(), new UpdateItemRequest());

        assertEquals(itemDto, itemDtoUpdate, "Обновленная вещь не совпадает с ожидаемой");
    }

    @Test
    void getItem_whenValidRequest_thenReturnsItemDto() throws Exception {
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new User()));

        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(item));

        when(bookingRepository.findByItem_Id(any(), any()))
                .thenReturn(new ArrayList<>());

        ItemDto itemDtoGet = itemService.get(user.getId(), item.getId());

        assertEquals(itemDto, itemDtoGet, "Полученная вещь не совпадает с ожидаемой");
    }

    @Test
    void getAllUserItems_whenValidRequest_thenReturnsItemDtos() throws Exception {
        itemDto.setComments(new ArrayList<>());

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new User()));

        when(itemRepository.findByOwnerId(any()))
                .thenReturn(new ArrayList<>(List.of(item)));

        when(bookingRepository.findByItem_Id(any(), any()))
                .thenReturn(new ArrayList<>());

        when(commentRepository.findByItemId(any()))
                .thenReturn(new ArrayList<>());

        List<ItemDto> itemDtos = itemService.getAllUserItems(user.getId());

        assertEquals(itemDto, itemDtos.getFirst(), "Полученная вещь не совпадает с ожидаемой");
    }

    @Test
    void searchItem_whenValidRequest_thenReturnsItemDto() throws Exception {
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new User()));

        when(itemRepository.search(any()))
                .thenReturn(new ArrayList<>(List.of(item)));


        List<ItemDto> itemDtos = itemService.search(user.getId(), "text");

        assertEquals(itemDto, itemDtos.getFirst(), "Полученная вещь не совпадает с ожидаемой");
    }

    @Test
    void createComment_whenValidRequest_thenReturnsCommentDto() throws Exception {
        LocalDateTime createdComment = LocalDateTime.now().minusDays(1);

        CommentDto commentDto = new CommentDto(
                1L,
                "text",
                item,
                "bob",
                createdComment
        );

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("text");
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(createdComment);

        Booking booking = new Booking(
                1L,
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().minusDays(2),
                item,
                user,
                OrderStatus.APPROVED
        );

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(new User()));

        when(itemRepository.findById(any()))
                .thenReturn(Optional.ofNullable(item));

        when(bookingRepository.findByItem_Id(any(), any()))
                .thenReturn(new ArrayList<>(List.of(booking)));

        when(commentRepository.save(any()))
                .thenReturn(comment);

        CommentDto commentDtoCreated = itemService.createComment(user.getId(), item.getId(), new NewCommentRequest());

        assertEquals(commentDto, commentDtoCreated, "Полученный коммент не совпадает с ожидаемым");
    }
}
