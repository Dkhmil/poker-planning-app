package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.khmil.management.enums.UserStoryStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStoryResponse {

    private Long id;

    private String description;

    private int votes;

    private UserStoryStatus status;

}

