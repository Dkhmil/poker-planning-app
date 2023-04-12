package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStoryVotationStatus {

    private int emittedVotes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> votedMembers;

}
