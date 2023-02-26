package com.dnd8th.error.exception.task;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class TaskAccessDeniedException extends AccessDeniedException {

    public TaskAccessDeniedException() {
        super(ErrorCode.TASK_ACCESS_DENIED);
    }
}
