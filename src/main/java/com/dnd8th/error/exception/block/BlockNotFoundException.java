package com.dnd8th.error.exception.block;

import com.dnd8th.error.exception.EntityNotFoundException;
import com.dnd8th.error.exception.ErrorCode;

public class BlockNotFoundException extends EntityNotFoundException {

    public BlockNotFoundException() {
        super(ErrorCode.BLOCK_NOT_FOUND);
    }
}
