package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.request.NewItemRequest;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {

    public static Item mapToNewItem(Long owner, NewItemRequest newItemRequest) {
        Item item = new Item(
                newItemRequest.getName(),
                newItemRequest.getDescription(),
                newItemRequest.getAvailable(),
                owner
        );


        if (newItemRequest.getRequest() != null) {
            item.setRequest(newItemRequest.getRequest());
        }

        return item;
    }

    public static ItemDto mapToItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner()
        );
    }

    public static ItemDto mapToItemBookingDto(Item item, LocalDateTime lastBooking, LocalDateTime nextBooking) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                lastBooking,
                nextBooking
        );
    }
}
