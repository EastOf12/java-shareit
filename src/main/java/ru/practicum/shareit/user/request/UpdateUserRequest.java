package ru.practicum.shareit.user.request;

import lombok.Data;
import ru.practicum.shareit.user.anotation.EmailCorrect;
import ru.practicum.shareit.user.anotation.EmailUnique;

@Data
public class UpdateUserRequest {
    private String name;

    @EmailUnique
    @EmailCorrect
    private String email;
}
