package com.dnd8th.error.exception.keep;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class KeepAccessDeniedException extends AccessDeniedException {
    public KeepAccessDeniedException() {
        super(ErrorCode.KEEP_ACCESS_DENIED);
    }
}
