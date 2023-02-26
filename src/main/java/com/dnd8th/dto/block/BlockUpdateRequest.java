package com.dnd8th.dto.block;

import com.dnd8th.util.validator.ColorHex;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlockUpdateRequest {

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
    public BlockUpdateRequest(String title, String emoticon, String blockColor,
            Boolean isSecret) {
        this.title = title;
        this.emoticon = emoticon;
        this.blockColor = blockColor;
        this.isSecret = isSecret;
    }
}
