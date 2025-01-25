package ru.practicum.shareit.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.user.anotation.EmailCorrect;
import ru.practicum.shareit.user.exception.EmailCorrectException;

public class EmailCorrectValidation implements ConstraintValidator<EmailCorrect, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.contains("@")) {
            return true;
        } else {
            throw new EmailCorrectException("Email некорректен");
        }
    }
}
