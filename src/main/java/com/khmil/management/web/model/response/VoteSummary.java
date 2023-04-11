package com.khmil.management.web.model.response;

import com.khmil.management.dal.entity.Vote;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class VoteSummary {
    private List<Integer> voteValues;
    private int totalVotes;
    private double averageVote;
    private int maxVote;
    private int minVote;
    private Map<String, Integer> nameValues;

    public VoteSummary(List<Vote> votes) {
        this.voteValues = votes.stream().map(Vote::getValue).toList();
        this.totalVotes = votes.size();
        this.averageVote = voteValues.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        this.maxVote = voteValues.stream().mapToInt(Integer::intValue).max().orElse(0);
        this.minVote = voteValues.stream().mapToInt(Integer::intValue).min().orElse(0);
        this.nameValues = votes.stream()
                .collect(Collectors.toMap(Vote -> Vote.getUser().getName(), Vote::getValue));
    }

    public List<Integer> getVotes() {
        return voteValues;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public double getAverageVote() {
        return averageVote;
    }

    public int getMaxVote() {
        return maxVote;
    }

    public int getMinVote() {
        return minVote;
    }
}
