package ru.practicum.shareit.request.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewItemRequest {

    @NotBlank
    private String description;
}
