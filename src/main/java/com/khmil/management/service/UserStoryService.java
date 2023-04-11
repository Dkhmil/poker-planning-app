package com.khmil.management.service;

import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.web.model.UserStoryVotationStatus;

public interface UserStoryService {
    UserStory addUserStory(Long sessionId, String userStoryId, String description);
    void deleteUserStory(Long id);

    UserStoryVotationStatus getUserStoryVotationStatus(Long userStoryId);

    void submitVote(Long sessionId, Long userStoryId, String voterName, int voteOption);
    void startVoting(Long sessionId, Long userStoryId);

    void stopVoting(Long userStoryId);
}
