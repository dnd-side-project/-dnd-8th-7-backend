package com.dnd8th.error.exception.block;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class BlockAccessDeniedException extends AccessDeniedException {

    public BlockAccessDeniedException() {
        super(ErrorCode.BLOCK_ACCESS_DENIED);
    }
}
