package com.dnd8th.error.exception.task;

import com.dnd8th.error.exception.EntityNotFoundException;
import com.dnd8th.error.exception.ErrorCode;

public class TaskNotFoundException extends EntityNotFoundException {

    public TaskNotFoundException() {
        super(ErrorCode.Task_NOT_FOUND);
    }
}
