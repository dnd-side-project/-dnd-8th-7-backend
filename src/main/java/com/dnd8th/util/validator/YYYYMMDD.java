package com.dnd8th.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YYYYMMDDValidator.class)
public @interface YYYYMMDD {

    String message() default "Invalid date format. Expected format is yyyy-MM-dd.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
