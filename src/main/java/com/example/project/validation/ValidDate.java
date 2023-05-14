package com.example.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.ZonedDateTime;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidClosedate.class)
public @interface ValidDate {
    String message() default "Validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
     String value() ;
    String errorCode() default "closeDate";
}
