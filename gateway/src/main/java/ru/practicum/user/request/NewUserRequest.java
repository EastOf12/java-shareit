package ru.practicum.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.user.email.EmailCorrect;

@Data
public class NewUserRequest {
    @NotBlank
    private String name;

    @EmailCorrect
    @NotBlank
    private String email;
}