package ru.practicum.shareit.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.user.anotation.EmailCorrect;
import ru.practicum.shareit.user.anotation.EmailUnique;

@Data
public class NewUserRequest {
    @NotBlank
    private String name;

    @EmailUnique
    @EmailCorrect
    @NotBlank
    private String email;
}