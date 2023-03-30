package com.dnd8th.error.exception.report;

import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.InvalidValueException;

public class DayInputInvalidException extends InvalidValueException {

    public DayInputInvalidException() {
        super(ErrorCode.DAY_INPUT_INVALID);
    }
}
