package com.khmil.management.web.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStoryRequest {
    Long sessionId;
    String description;
}
