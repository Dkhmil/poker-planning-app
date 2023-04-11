package com.khmil.management.service;

import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.web.model.UserStoryVotationStatus;
import com.khmil.management.web.model.response.VoteResult;
import com.khmil.management.web.model.response.VoteSummary;

public interface UserStoryService {
    UserStory addUserStory(Long sessionId, String userStoryId, String description);
    void deleteUserStory(Long id);

    UserStoryVotationStatus getUserStoryVotationStatus(Long userStoryId);

    VoteResult submitVote(Long userStoryId, String voterName, int voteOption);
    void startVoting(Long userStoryId);

    VoteSummary stopVoting(Long userStoryId);
}
