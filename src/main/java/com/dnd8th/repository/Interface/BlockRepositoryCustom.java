package com.dnd8th.repository.Interface;

import java.util.Date;
import java.util.List;

public interface BlockRepositoryCustom {
    List<String> findByIdAndDate(long id, Date date);
}
