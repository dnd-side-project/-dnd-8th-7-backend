package com.dnd8th.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ColorHexValidator implements ConstraintValidator<ColorHex, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^#([A-Fa-f0-9]{6})$");
    }
}
