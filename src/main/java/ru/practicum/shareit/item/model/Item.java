package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;
    private Long request;

    public Item(String name, String description, Boolean available, Long owner) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }
}
