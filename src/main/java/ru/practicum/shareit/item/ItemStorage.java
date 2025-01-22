package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ItemNotExistException;
import ru.practicum.shareit.item.exception.UserNotOwnerItemException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.request.UpdateItemRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class ItemStorage {
    private final HashMap<Long, Item> items;

    public ItemStorage() {
        items = new HashMap<>();
    }

    public ItemDto create(Long owner, NewItemRequest newItemRequest) {
        Item item = ItemMapper.mapToNewItem(owner, newItemRequest);
        item.setId(idGeneration());
        items.put(item.getId(), item);
        log.info("Создаем вещь {}", item.getId());

        return ItemMapper.mapToItemDto(item);
    }

    public ItemDto update(Long userId, Long itemId, UpdateItemRequest updateItemRequest) {
        Item updateItem = items.get(itemId);

        if(updateItem == null) {
            throw new ItemNotExistException("Вещь " + itemId + " не существует");
        }

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

        items.put(updateItem.getId(), updateItem);
        log.info("Обновляем вещь {}", itemId);

        return ItemMapper.mapToItemDto(updateItem);
    }

    public ItemDto get(Long userId, Long itemId) {
        Item item = items.get(itemId);

        if(item == null) {
            throw new ItemNotExistException("Вещь " + itemId + " не существует");
        }

        log.info("Предаем информацию о вещи {} пользователю {}", itemId, userId);
        return ItemMapper.mapToItemDto(items.get(itemId));
    }

    private Long idGeneration() {
        if (items.isEmpty()) {
            return 1L;
        } else {
            Item item = items.get((long) items.size());
            return (item.getId() + 1L);
        }
    }

    public List<ItemDto> getAllUserItems(Long userId) {
        List<ItemDto> itemDtos = new ArrayList<>();

        for (Item item : items.values()) {
            if (userId.equals(item.getOwner())) {
                itemDtos.add(ItemMapper.mapToItemDto(item));
            }
        }

        log.info("Предаем информацию о вещах пользователя {}", userId);

        return itemDtos;
    }

    public List<ItemDto> search(Long userId, String text) {
        List<ItemDto> itemDtos = new ArrayList<>();
        log.info("Производим поиск вещи пользователем {} по запросу - {}", userId, text);

        if (!text.isEmpty()) {
            String lowerText = text.toLowerCase();
            for (Item item : items.values()) {
                if (item.getAvailable() && (item.getName().toLowerCase().contains(lowerText) || item.getDescription()
                        .toLowerCase().contains(lowerText))) {

                    itemDtos.add(ItemMapper.mapToItemDto(item));
                }
            }
        }

        return itemDtos;
    }
}
