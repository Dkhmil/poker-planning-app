package com.khmil.management.service;

import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.web.model.UserStoryVotationStatus;
import com.khmil.management.web.model.request.UserStoryRequest;
import com.khmil.management.web.model.request.VoteRequest;
import com.khmil.management.web.model.response.VoteResult;
import com.khmil.management.web.model.response.VoteSummary;

public interface UserStoryService {
    UserStory addUserStory(UserStoryRequest request);
    void deleteUserStory(Long id);

    UserStory getUserStory(Long id);

    UserStoryVotationStatus getUserStoryVotationStatus(Long userStoryId);

    VoteResult submitVote(Long userStoryId, VoteRequest request);
    void startVoting(Long userStoryId);

    VoteSummary stopVoting(Long userStoryId);
}
