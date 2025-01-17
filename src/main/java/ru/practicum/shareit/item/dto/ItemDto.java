package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private Long owner;
    private Long request;

    public ItemDto(Long id, String name, String description, boolean available, Long owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }
}
