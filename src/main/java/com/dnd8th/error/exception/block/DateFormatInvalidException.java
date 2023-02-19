package com.dnd8th.error.exception.block;

import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.FormattingException;

public class DateFormatInvalidException extends FormattingException {

    public DateFormatInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}
