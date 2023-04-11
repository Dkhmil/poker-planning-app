package com.khmil.management.web.model.response;

import com.khmil.management.enums.UserStoryStatus;
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
public class UserStoryResponse {
    private Long id;
    private String description;
    private int votes;
    private UserStoryStatus status;

}

