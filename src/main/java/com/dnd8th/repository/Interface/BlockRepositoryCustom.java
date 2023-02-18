package com.dnd8th.repository.Interface;

import com.dnd8th.entity.Block;

import java.util.Date;
import java.util.List;

public interface BlockRepositoryCustom {
    List<Block> findByIdAndDate(long id, Date date);
}
