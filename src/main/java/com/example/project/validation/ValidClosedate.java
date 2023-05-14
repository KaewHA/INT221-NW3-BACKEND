package com.example.project.validation;

import com.example.project.DTO.createanno;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.ZonedDateTime;

public class ValidClosedate implements ConstraintValidator<ValidDate, Boolean> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Boolean result, ConstraintValidatorContext constraintValidatorContext) {
        return result;
    }
}