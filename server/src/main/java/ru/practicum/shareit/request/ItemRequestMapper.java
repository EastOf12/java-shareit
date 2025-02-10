package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    public static ItemRequest mapItemRequest(NewItemRequest newItemRequest, User owner) {
        return new ItemRequest(
                newItemRequest.getDescription(),
                owner,
                LocalDateTime.now()
        );
    }

    public static ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest, List<Item> items) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getOwner(),
                itemRequest.getCreated(),
                items
        );
    }
}
