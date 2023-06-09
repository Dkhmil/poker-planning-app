package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionResponse {

    String link;

    Long sessionId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<UserStoryResponse> userStories;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<UserResponse> members;

    String title;
}
