package com.dnd8th.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.vdurmont.emoji.EmojiManager;

public class EmojiValidator implements ConstraintValidator<Emoji, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmojiManager.isEmoji(value);
    }
}
