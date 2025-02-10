package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto create(@Valid @RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestBody NewItemRequest newItemRequest) {
        return itemRequestService.create(userId, newItemRequest);
    }

    @GetMapping
    public List<ItemRequestDto> getUserItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getUserItemRequest(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        return itemRequestService.getItemRequest(userId, requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getItemRequestAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getItemRequestAll(userId);
    }
}
