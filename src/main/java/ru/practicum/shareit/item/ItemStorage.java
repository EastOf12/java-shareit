package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.user.User;

import java.util.HashMap;

@Slf4j
@Repository
public class ItemStorage {
    private final HashMap<Long, User> items;

    public ItemStorage() {
        items = new HashMap<>();
    }

    public ItemDto create(Long owner, NewItemRequest newItemRequest) {
        Item item = ItemMapper.mapToNewItem(owner, newItemRequest);
        item.setId(idGeneration());
        return ItemMapper.mapToItemDto(item);
    }

    private Long idGeneration() {
        if(items.isEmpty()) {
            return 1L;
        } else {
            User us = items.get((long) items.size());
            return (us.getId() + 1L);
        }
    }
}
