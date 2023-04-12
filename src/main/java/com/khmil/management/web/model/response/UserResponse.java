package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    Long id;

    String name;

    Long sessionId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<Long> voteIds;
}
