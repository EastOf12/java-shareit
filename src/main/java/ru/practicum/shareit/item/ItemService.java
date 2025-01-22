package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ItemNotExistException;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.request.UpdateItemRequest;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemDto create(Long owner, NewItemRequest newItemRequest) {
        userStorage.get(owner);

        return itemStorage.create(owner, newItemRequest);
    }

    public ItemDto update(Long userId, Long itemId, UpdateItemRequest updateItemRequest) {
        userStorage.get(userId);
        itemStorage.get(userId, itemId);

        return itemStorage.update(userId, itemId, updateItemRequest);
    }

    public ItemDto get(Long userId, Long itemId) {
        userStorage.get(userId);
        itemStorage.get(userId, itemId);

        return itemStorage.get(userId, itemId);
    }

    public List<ItemDto> getAllUserItems(Long userId) {
        userStorage.get(userId);

        return itemStorage.getAllUserItems(userId);
    }

    public List<ItemDto> search(Long userId, String text) {
        userStorage.get(userId);

        return itemStorage.search(userId, text);
    }
}
