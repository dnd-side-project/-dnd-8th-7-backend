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

    @NotBlank
    private String emoji;

    @ColorHex
    @NotBlank
    private String backgroundColor;

    @NotNull
    private Boolean isSecret;

    @Builder
    public BlockCreateRequest(String date, String title, String emoji, String backgroundColor,
            Boolean isSecret) {
        this.date = date;
        this.title = title;
        this.emoji = emoji;
        this.backgroundColor = backgroundColor;
        this.isSecret = isSecret;
    }

    @Builder
    public Block toEntity(User user, Date date) {
        return Block.builder()
                .user(user)
                .blockLock(isSecret)
                .title(title)
                .emotion(emoji)
                .blockColor(backgroundColor)
                .date(date)
                .build();
    }
}
