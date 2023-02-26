package com.dnd8th.dto.keep;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class KeepCreateRequest {
    @NotNull
    private List<Long> blockIds = new ArrayList<>();

    @Builder
    public KeepCreateRequest(List<Long> blockIds) {
        this.blockIds = blockIds;
    };
}
