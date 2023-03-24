package com.dnd8th.error.exception.report;

import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.InvalidValueException;

public class MonthInputInvalidException extends InvalidValueException {

    public MonthInputInvalidException() {
        super(ErrorCode.MONTH_INPUT_INVALID);
    }
}
