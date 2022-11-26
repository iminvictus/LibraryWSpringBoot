package ru.ratnikov.spring.LibraryBoot.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrentYearValidator.class)
@Documented
public @interface CurrentYear {
    String message() default "Year cannot be greater than current";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}
