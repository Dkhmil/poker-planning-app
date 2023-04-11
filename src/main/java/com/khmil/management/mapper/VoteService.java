package com.khmil.management.mapper;

import com.khmil.management.exception.UserStoryNotFoundException;
import com.khmil.management.web.model.response.VoteResponse;

import java.util.List;

public interface VoteService {

    void castVote(Long userStoryId, int voteValue, Long voterId)
            throws UserStoryNotFoundException;

    Integer getVoteForUserStoryByMember(Long userStoryId, Long memberId)
            throws UserStoryNotFoundException;

    int getNumVotesForUserStory(Long userStoryId) throws UserStoryNotFoundException;

    int getSumVotesForUserStory(Long userStoryId) throws UserStoryNotFoundException;

    double getAvgVoteForUserStory(Long userStoryId) throws UserStoryNotFoundException;

    void closeVoting(Long userStoryId) throws UserStoryNotFoundException;

    List<VoteResponse> getVotesByUserStoryId(Long userStoryId);

}
