package com.dnd8th.dto.block;

import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.util.validator.ColorHex;
import com.dnd8th.util.validator.YYYYMMDD;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlockCreateRequest {

    @YYYYMMDD
    @NotBlank
    private String date;

    @NotBlank
    private String title;

    //    @Emoji
    @NotBlank
    private String emoticon;

    @ColorHex
    @NotBlank
    private String blockColor;

    @NotNull
    private Boolean isSecret;

    @Builder
    public BlockCreateRequest(String date, String title, String emoticon, String blockColor,
            Boolean isSecret) {
        this.date = date;
        this.title = title;
        this.emoticon = emoticon;
        this.blockColor = blockColor;
        this.isSecret = isSecret;
    }

    @Builder
    public Block toEntity(User user, Date date) {
        return Block.builder()
                .user(user)
                .blockLock(isSecret)
                .title(title)
                .emotion(emoticon)
                .blockColor(blockColor)
                .date(date)
                .build();
    }
}
