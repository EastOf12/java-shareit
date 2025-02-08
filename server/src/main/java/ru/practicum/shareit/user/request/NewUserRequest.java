package ru.practicum.shareit.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.user.anotation.EmailCorrect;

@Data
public class NewUserRequest {
    @NotBlank
    private String name;

    @EmailCorrect
    @NotBlank
    private String email;
}