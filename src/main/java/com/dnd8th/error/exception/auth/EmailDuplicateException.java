package com.dnd8th.error.exception.auth;

import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.InvalidValueException;

public class EmailDuplicateException extends InvalidValueException {

    public EmailDuplicateException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
