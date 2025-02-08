package ru.practicum.user.request;

import lombok.Data;
import ru.practicum.user.email.EmailCorrect;

@Data
public class UpdateUserRequest {
    private String name;

    @EmailCorrect
    private String email;
}
