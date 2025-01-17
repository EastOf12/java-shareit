package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.request.NewItemRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {

    public static Item mapToNewItem(Long owner, NewItemRequest newItemRequest) {
        Item item = new Item(
                newItemRequest.getName(),
                newItemRequest.getDescription(),
                newItemRequest.getAvailable(),
                owner
        );


        if(newItemRequest.getRequest() != null) {
            item.setRequest(newItemRequest.getRequest());
        }

        return item;
    }

    public static ItemDto mapToItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getOwner()
        );
    }
}
