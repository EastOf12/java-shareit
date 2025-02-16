package ru.practicum.shareit.user.request;

import lombok.Data;

@Data
public class NewUserRequest {
    private String name;
    private String email;
}