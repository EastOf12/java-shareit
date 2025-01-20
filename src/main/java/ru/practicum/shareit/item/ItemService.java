package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ItemNotExistException;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.request.UpdateItemRequest;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.List;

@Service
@Slf4j
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemService(ItemStorage itemStorage, UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    public ItemDto create(Long owner, NewItemRequest newItemRequest) {
        if (!userStorage.userExists(owner)) {
            throw new UserNotFoundException("Пользователь " + owner + " не существует");
        }

        return itemStorage.create(owner, newItemRequest);
    }

    public ItemDto update(Long userId, Long itemId, UpdateItemRequest updateItemRequest) {
        if (!userStorage.userExists(userId)) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        if (itemStorage.itemExists(itemId)) {
            throw new ItemNotExistException("Вещь " + itemId + " не существует");
        }

        return itemStorage.update(userId, itemId, updateItemRequest);
    }

    public ItemDto get(Long userId, Long itemId) {
        if (!userStorage.userExists(userId)) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        if (itemStorage.itemExists(itemId)) {
            throw new ItemNotExistException("Вещь " + itemId + " не существует");
        }

        return itemStorage.get(userId, itemId);
    }

    public List<ItemDto> getAllUserItems(Long userId) {
        if (!userStorage.userExists(userId)) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        return itemStorage.getAllUserItems(userId);
    }

    public List<ItemDto> search(Long userId, String text) {
        if (!userStorage.userExists(userId)) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        return itemStorage.search(userId, text);
    }
}
