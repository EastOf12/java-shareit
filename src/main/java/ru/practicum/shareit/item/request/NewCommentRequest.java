package ru.practicum.shareit.item.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewCommentRequest {
    @NotBlank
    private String text;
}
