package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.exception.UserNotFoundException;

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
        if(!userStorage.userExists(owner)) {
            throw new UserNotFoundException("Пользователь " + owner + " не существует");
        }

        return itemStorage.create(owner, newItemRequest);
    }
}
