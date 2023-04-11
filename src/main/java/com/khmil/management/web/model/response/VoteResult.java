package com.khmil.management.web.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VoteResult {
    private int voteCount;
    private List<String> voters;
    private String summary;

    public VoteResult(int voteCount, List<String> voters, String summary) {
        this.voteCount = voteCount;
        this.voters = voters;
        this.summary = summary;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<String> getVoters() {
        return voters;
    }

    public String getSummary() {
        return summary;
    }
}
