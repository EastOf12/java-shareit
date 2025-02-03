package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.exeption.BookingNotValidException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.UserNotOwnerItemException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.request.NewCommentRequest;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.request.UpdateItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(Long owner, NewItemRequest newItemRequest) {
        log.info("Создаем вещь");

        if (userRepository.findById(owner).isEmpty()) {
            throw new NotFoundException("Пользователь " + owner + " не найден");
        }

        Item item = itemRepository.save(ItemMapper.mapToNewItem(owner, newItemRequest));
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto update(Long userId, Long itemId, UpdateItemRequest updateItemRequest) {
        log.info("Обновляем вещь {}", itemId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        Item updateItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь " + itemId + " не найдена"));

        if (!Objects.equals(updateItem.getOwner(), userId)) {
            throw new UserNotOwnerItemException("Пользователь не владелец вещи");
        }

        if (updateItemRequest.getName() != null && !updateItemRequest.getName().isEmpty()) {
            updateItem.setName(updateItemRequest.getName());
        }

        if (updateItemRequest.getDescription() != null && !updateItemRequest.getDescription().isEmpty()) {
            updateItem.setDescription(updateItemRequest.getDescription());
        }

        if (updateItemRequest.getAvailable() != null) {
            updateItem.setAvailable(updateItemRequest.getAvailable());
        }

        if (updateItemRequest.getRequest() != null) {
            updateItem.setRequest(updateItemRequest.getRequest());
        }

        return ItemMapper.mapToItemDto(itemRepository.save(updateItem));
    }

    @Override
    public ItemDto get(Long userId, Long itemId) {
        log.info("Предаем информацию о вещи {} пользователю {}", itemId, userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь " + itemId + " не найдена"));

        Sort sort = Sort.by(Sort.Direction.ASC, "start");
        List<Booking> bookings = bookingRepository.findByItem_Id(itemId, sort);

        if (bookings.isEmpty()) {
            return ItemMapper.mapToItemDto(item);
        }

        LocalDateTime nextBooking = null;
        LocalDateTime lastBooking = null;
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : bookings) {
            if (booking.getEnd().isAfter(now)) {
                lastBooking = booking.getEnd();
            }
            if (booking.getStart().isAfter(now) && nextBooking == null) {
                nextBooking = booking.getStart();
            }
        }

        ItemDto itemDto = ItemMapper.mapToItemBookingDto(item, lastBooking, nextBooking);
        List<Comment> comments = commentRepository.findByItemId(itemId);
        itemDto.setComments(comments);

        return itemDto;
    }

    @Override
    public List<ItemDto> getAllUserItems(Long userId) {
        log.info("Предаем информацию о вещах пользователя {}", userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        List<Item> items = itemRepository.findByOwner(userId);
        List<ItemDto> itemDtos = new ArrayList<>();

        Sort sort = Sort.by(Sort.Direction.ASC, "start");
        LocalDateTime now = LocalDateTime.now();

        for (Item item : items) {
            List<Comment> comments = commentRepository.findByItemId(item.getId());

            if (userId.equals(item.getOwner())) {
                LocalDateTime lastBooking = null;
                LocalDateTime nextBooking = null;

                List<Booking> bookings = bookingRepository.findByItem_Id(item.getId(), sort);

                if (bookings.isEmpty()) {
                    ItemDto iDt = ItemMapper.mapToItemDto(item);
                    iDt.setComments(comments);
                    itemDtos.add(iDt);
                } else {

                    for (Booking booking : bookings) {
                        if (booking.getEnd().isAfter(now)) {
                            lastBooking = booking.getEnd();
                        }
                        if (booking.getStart().isAfter(now) && nextBooking == null) {
                            nextBooking = booking.getStart();
                        }
                    }

                    ItemDto iDt = ItemMapper.mapToItemBookingDto(item, lastBooking, nextBooking);
                    iDt.setComments(comments);
                    itemDtos.add(iDt);
                }
            }
        }

        return itemDtos;
    }

    @Override
    public List<ItemDto> search(Long userId, String text) {
        log.info("Производим поиск вещи пользователем {} по запросу - {}", userId, text);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        List<Item> items = itemRepository.search(text);
        List<ItemDto> itemDtos = new ArrayList<>();

        for (Item item : items) {
            itemDtos.add(ItemMapper.mapToItemDto(item));
        }

        return itemDtos;
    }

    @Override
    public CommentDto createComment(Long userId, Long itemId, NewCommentRequest newCommentRequest) {

        log.info("Пользователь {} создает комментарий на вещь {}", userId, itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь " + itemId + " не найдена"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь " + userId + " не найден"));

        Sort sort = Sort.by(Sort.Direction.ASC, "start");
        List<Booking> bookings = bookingRepository.findByItem_Id(itemId, sort);

        boolean isBooking = false;

        for (Booking booking : bookings) {
            if (userId.equals(booking.getBooker().getId()) && booking.getEnd().isBefore(LocalDateTime.now())) {
                isBooking = !isBooking;
            }
        }

        if (!isBooking) {
            throw new BookingNotValidException("Пользователь не может оставить комментарий");
        }

        Comment comment = commentRepository.save(CommentMapper.mapToNewComment(userId, newCommentRequest, item, user));

        return CommentMapper.mapToCommentDto(comment);
    }
}
