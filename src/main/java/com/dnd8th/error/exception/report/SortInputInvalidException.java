package com.dnd8th.error.exception.report;

import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.InvalidValueException;

public class SortInputInvalidException extends InvalidValueException {

    public SortInputInvalidException() {
        super(ErrorCode.SORT_INPUT_INVALID);
    }
}
