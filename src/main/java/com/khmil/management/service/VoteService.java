package com.khmil.management.service;

import com.khmil.management.dal.entity.Vote;

public interface VoteService {
    Vote startVoting(Long userStoryId); // +
    Vote vote(Long voteId, Long userId, int value); // +
    Vote finishVoting(Long voteId);
}