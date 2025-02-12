package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;

import java.util.List;


public interface ItemRequestService {

    ItemRequestDto create(Long userId, NewItemRequest newItemRequest);

    List<ItemRequestDto> getUserItemRequest(Long userId);

    ItemRequestDto getItemRequest(Long userId, Long requestId);

    List<ItemRequestDto> getItemRequestAll(Long userId);
}
