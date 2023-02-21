package com.dnd8th.dto.block;

import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlockCreateRequest {

    @NotBlank
    private String date;

    @NotBlank
    private String blockTitle;

    @NotBlank
    private String emoticon;
    @NotBlank
    private String blockColor;
    
    private Boolean isSecret;

    @Builder
    public BlockCreateRequest(String date, String blockTitle, String emoticon, String blockColor,
            Boolean isSecret) {
        this.date = date;
        this.blockTitle = blockTitle;
        this.emoticon = emoticon;
        this.blockColor = blockColor;
        this.isSecret = isSecret;
    }

    @Builder
    public Block toEntity(User user, Date date) {
        return Block.builder()
                .user(user)
                .blockLock(isSecret)
                .save(false)
                .title(blockTitle)
                .emotion(emoticon)
                .blockColor(blockColor)
                .date(date)
                .build();
    }
}
