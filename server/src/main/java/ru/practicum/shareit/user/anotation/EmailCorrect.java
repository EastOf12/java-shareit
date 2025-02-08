package ru.practicum.shareit.user.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.practicum.shareit.user.validation.EmailCorrectValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailCorrectValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailCorrect {
    String message() default "Email некорректен";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
