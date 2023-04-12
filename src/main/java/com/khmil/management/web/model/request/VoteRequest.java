package com.khmil.management.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequest {

    @NotBlank
    String voterName;

    @NotNull
    Long voteOption;
}
