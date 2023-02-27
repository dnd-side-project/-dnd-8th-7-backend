package com.dnd8th.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmojiValidator implements ConstraintValidator<Emoji, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches("(\u00a9|\u00ae|[\u2000-\u3300]|\ud83c[\ud000-\udfff]|\ud83d[\ud000-\udfff]|\ud83e[\ud000-\udfff])");
    }
}
