package ru.practicum.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody NewItemRequest newItemRequest) {
        return itemRequestClient.create(userId, newItemRequest);
    }


    @GetMapping
    public ResponseEntity<Object> getUserItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.getUserItemRequest(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long requestId) {
        return itemRequestClient.getItemRequest(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getItemRequestAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.getItemRequestAll(userId);
    }
}
