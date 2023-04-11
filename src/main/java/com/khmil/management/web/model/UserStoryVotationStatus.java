package com.khmil.management.web.model;

import java.util.List;

public class UserStoryVotationStatus {
    private int emittedVotes;
    private List<String> votedMembers;

    public UserStoryVotationStatus(int emittedVotes, List<String> votedMembers) {
        this.emittedVotes = emittedVotes;
        this.votedMembers = votedMembers;
    }

    public int getEmittedVotes() {
        return emittedVotes;
    }

    public List<String> getVotedMembers() {
        return votedMembers;
    }
}
