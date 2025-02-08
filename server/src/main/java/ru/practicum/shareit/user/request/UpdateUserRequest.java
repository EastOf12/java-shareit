package ru.practicum.shareit.user.request;

import lombok.Data;
import ru.practicum.shareit.user.anotation.EmailCorrect;

@Data
public class UpdateUserRequest {
    private String name;

    @EmailCorrect
    private String email;
}
