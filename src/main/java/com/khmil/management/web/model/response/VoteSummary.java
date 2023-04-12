package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.khmil.management.dal.entity.Vote;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteSummary {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> voteValues;

    private int totalVotes;

    private double averageVote;

    private int maxVote;

    private int minVote;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Long> nameValues;

    public VoteSummary(List<Vote> votes) {
        this.voteValues = votes.stream().map(Vote::getVoteOption).toList();
        this.totalVotes = votes.size();
        this.averageVote = voteValues.stream().mapToInt(Long::intValue).average().orElse(0.0);
        this.maxVote = voteValues.stream().mapToInt(Long::intValue).max().orElse(0);
        this.minVote = voteValues.stream().mapToInt(Long::intValue).min().orElse(0);
        this.nameValues = votes.stream()
                .collect(Collectors.toMap(Vote -> Vote.getUser().getName(), Vote::getVoteOption));
    }

}
