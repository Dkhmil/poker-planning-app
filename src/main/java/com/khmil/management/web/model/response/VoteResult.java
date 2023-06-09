package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResult {

    private int voteCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> voters;

    private String summary;

}
