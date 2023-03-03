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

    @NotBlank
    private String emoji;

    @ColorHex
    @NotBlank
    private String backgroundColor;

    @NotNull
    private Boolean isSecret;

    @Builder
    public BlockUpdateRequest(String title, String emoji, String backgroundColor,
            Boolean isSecret) {
        this.title = title;
        this.emoji = emoji;
        this.backgroundColor = backgroundColor;
        this.isSecret = isSecret;
    }
}
