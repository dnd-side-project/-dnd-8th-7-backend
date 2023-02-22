package com.dnd8th.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmojiValidator.class)
public @interface Emoji {

    String message() default "Invalid Unicode.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
