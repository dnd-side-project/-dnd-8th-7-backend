package com.dnd8th.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNameDuplicatedResponse {

    private boolean duplicated;

    @Builder
    public UserNameDuplicatedResponse(boolean duplicated) {
        this.duplicated = duplicated;
    }
}
