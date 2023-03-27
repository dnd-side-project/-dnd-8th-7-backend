package com.dnd8th.dto.report;

import com.dnd8th.entity.Block;
import java.util.Date;
import lombok.Data;

@Data
public class MonthlyBlockGetDTO {

    private Long id;
    private String title;
    private Date date;

    public MonthlyBlockGetDTO(Block block) {
        this.id = block.getId();
        this.title = block.getTitle();
        this.date = block.getDate();
    }
}
