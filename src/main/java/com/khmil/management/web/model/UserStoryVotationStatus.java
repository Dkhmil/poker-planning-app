package com.khmil.management.web.model;

import lombok.Data;

import java.util.List;

@Data
public class UserStoryVotationStatus {

    private int emittedVotes;
    private List<String> votedMembers;

    public UserStoryVotationStatus(int emittedVotes, List<String> votedMembers) {
        this.emittedVotes = emittedVotes;
        this.votedMembers = votedMembers;
    }
}
