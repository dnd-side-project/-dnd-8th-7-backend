package com.dnd8th.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmojiValidator implements ConstraintValidator<Emoji, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("[^\\p{L}]+");
    }
}
