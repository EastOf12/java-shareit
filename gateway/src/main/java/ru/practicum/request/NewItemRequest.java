package ru.practicum.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewItemRequest {

    @NotBlank
    private String description;
}
