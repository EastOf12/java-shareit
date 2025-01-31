package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;
    private Long request;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;

    private List<Comment> comments;

    public ItemDto(Long id, String name, String description, boolean available, Long owner,
                   LocalDateTime lastBooking, LocalDateTime nextBooking) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }

    public ItemDto(Long id, String name, String description, boolean available, Long owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }
}
