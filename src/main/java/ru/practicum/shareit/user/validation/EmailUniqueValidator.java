package ru.practicum.shareit.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.anotation.EmailUnique;
import ru.practicum.shareit.user.exception.EmailAlreadyExistsException;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Autowired
    private UserStorage userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository.isEmailUnique(email)) {
            return true; // Email уникален
        } else {
            throw new EmailAlreadyExistsException("Email уже используется");
        }
    }
}


