package com.dnd8th.util.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YYYYMMDDValidator implements ConstraintValidator<YYYYMMDD, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        try {
            LocalDate.parse(value, FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
