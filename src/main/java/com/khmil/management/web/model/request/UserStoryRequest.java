package com.khmil.management.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserStoryRequest {

    @NotNull
    Long sessionId;

    @NotBlank
    String description;
}
