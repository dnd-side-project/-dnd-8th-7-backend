package com.dnd8th.error.exception.keep;

import com.dnd8th.error.exception.EntityNotFoundException;
import com.dnd8th.error.exception.ErrorCode;

public class KeepNotFoundException extends EntityNotFoundException {

    public KeepNotFoundException() {
        super(ErrorCode.KEEP_NOT_FOUND);
    }
}
