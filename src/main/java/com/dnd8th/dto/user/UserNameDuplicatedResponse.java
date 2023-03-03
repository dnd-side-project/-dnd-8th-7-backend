package com.dnd8th.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNameDuplicatedResponse {

    @JsonProperty("isDuplicated")
    private boolean duplicated;

    @Builder
    public UserNameDuplicatedResponse(boolean duplicated) {
        this.duplicated = duplicated;
    }
}
