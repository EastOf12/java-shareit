package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "owner", nullable = false)
    private Long owner;

    @Column(name = "request")
    private Long request;

    public Item(String name, String description, Boolean available, Long owner) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }
}
