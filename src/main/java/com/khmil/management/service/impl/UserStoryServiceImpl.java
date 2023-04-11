package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.UserStoryEntity;
import com.khmil.management.dal.repository.MemberRepository;
import com.khmil.management.dal.repository.UserStoryRepository;
import com.khmil.management.enums.UserStoryStatus;
import com.khmil.management.exception.UserStoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final MemberRepository userRepository;


    public void startVoting(Long userStoryId) {
        UserStoryEntity userStoryEntity = userStoryRepository.findById(String.valueOf(userStoryId))
                .orElseThrow(UserStoryNotFoundException::new);

        if (userStoryEntity.getStatus() != UserStoryStatus.PENDING && userStoryEntity.getStatus() != UserStoryStatus.VOTED) {

        }

        userStoryEntity.setStatus(UserStoryStatus.VOTING);
        userStoryRepository.save(userStoryEntity);
    }

    public void stopVoting(Long userStoryId) {
        UserStoryEntity userStoryEntity = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("User story not found with id " + userStoryId));

        if (userStoryEntity.getStatus() != UserStoryStatus.VOTING) {
            throw new UserStoryInvalidStatusException("User story cannot be stopped for voting because it is not in VOTING status");
        }

        userStoryEntity.setStatus(UserStoryStatus.VOTED);
        userStoryRepository.save(userStoryEntity);

    }

/*
    public VoteEntity voteUserStory(Long userStoryId, VoteRequest voteRequest) {
        UserStoryEntity userStoryEntity = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("User story not found with id " + userStoryId));

        if (userStoryEntity.getStatus() != UserStoryStatus.VOTING) {
            throw new UserStoryInvalidStatusException
*/
}