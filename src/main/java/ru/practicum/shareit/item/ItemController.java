package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.request.NewItemRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long owner, @Valid @RequestBody NewItemRequest newItemRequest) {
        return itemService.create(owner, newItemRequest);
    } //Создать вещь

    @PatchMapping("/{id}")
    public ItemDto update() {
        return null;
    } //Обновить вещь

    @GetMapping("/{id}")
    public ItemDto get() {
        return null;
    } //Получить вещь

    @DeleteMapping("/{id}")
    public void delete() {

    } //Удалить вещь
}
