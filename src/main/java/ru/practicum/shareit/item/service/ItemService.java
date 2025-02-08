package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.request.NewCommentRequest;
import ru.practicum.shareit.item.request.NewItemRequest;
import ru.practicum.shareit.item.request.UpdateItemRequest;

import java.util.List;

public interface ItemService {
    ItemDto create(Long owner, NewItemRequest newItemRequest);

    ItemDto update(Long userId, Long itemId, UpdateItemRequest updateItemRequest);

    ItemDto get(Long userId, Long itemId);

    List<ItemDto> getAllUserItems(Long userId);

    List<ItemDto> search(Long userId, String text);

    CommentDto createComment(Long userId, Long itemId, NewCommentRequest newCommentRequest);
}
