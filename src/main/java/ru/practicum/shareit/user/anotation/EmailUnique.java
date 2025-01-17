package ru.practicum.shareit.user.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.practicum.shareit.user.validation.EmailUniqueValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnique {
    String message() default "Email уже используется";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
