package ru.practicum.user.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
