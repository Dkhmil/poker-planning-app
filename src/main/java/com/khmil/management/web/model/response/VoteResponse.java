package com.khmil.management.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private Long userId;
    private Long userStoryId;
    private int voteValue;
}
