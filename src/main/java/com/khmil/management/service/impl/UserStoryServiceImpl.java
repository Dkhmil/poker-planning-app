package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.dal.entity.Vote;
import com.khmil.management.dal.repository.PokerPlanningSessionRepository;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.dal.repository.UserStoryRepository;
import com.khmil.management.dal.repository.VoteRepository;
import com.khmil.management.enums.UserStoryStatus;
import com.khmil.management.exception.ResourceNotFoundException;
import com.khmil.management.service.UserStoryService;
import com.khmil.management.web.model.UserStoryVotationStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final VoteRepository voteRepository;
    private final PokerPlanningSessionRepository sessionRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void startVoting(Long sessionId, Long userStoryId) {
        PokerPlanningSession session = getSessionById(sessionId);
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));
        userStory.setStatus(UserStoryStatus.VOTING);
        userStoryRepository.save(userStory);
        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void submitVote(Long sessionId, Long userStoryId, String voterName, int voteOption) {
        PokerPlanningSession session = getSessionById(sessionId);
        if (session == null) {
            throw new ResourceNotFoundException(sessionId);
        }
        User user = userRepository.findByName(voterName).get();

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));

        if (!session.getUserStories().contains(userStory)) {
            throw new IllegalArgumentException("User story not found in session with id " + sessionId);
        }

        if (!session.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is not a participant in session with id " + sessionId);
        }
        Vote vote = Vote.builder()
                .value(voteOption)
                .userStory(userStory)
                .user(user)
                .build();
        userStory.getVotes().add(vote);
        userStoryRepository.save(userStory);
        sessionRepository.save(session);
    }
    @Override
    public UserStoryVotationStatus getUserStoryVotationStatus(Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));

        if (userStory.getStatus() != UserStoryStatus.VOTING) {
            throw new IllegalStateException("User story is not in VOTING status");
        }

        List<Vote> votes = voteRepository.findByUserStoryId(userStoryId);

        int emittedVotes = votes.size();
        List<String> votedMembers = votes.stream()
                .map(vote -> vote.getUser().getName())
                .collect(Collectors.toList());

        return new UserStoryVotationStatus(emittedVotes, votedMembers);
    }

    @Override
    public void stopVoting(Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));

        if (userStory.getStatus() != UserStoryStatus.VOTING) {
            throw new IllegalStateException("User story is not in VOTING status");
        }
        userStory.setStatus(UserStoryStatus.VOTED);
        userStoryRepository.save(userStory);
    }


    @Override
    public UserStory addUserStory(Long sessionId, String userStoryId, String description) {
        PokerPlanningSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException( sessionId));

        UserStory userStory = new UserStory();
        userStory.setDescription(description);
        userStory.setStatus(UserStoryStatus.PENDING);

        userStory.setSession(session);
        session.getUserStories().add(userStory);

        userStoryRepository.save(userStory);

        return userStory;
    }

    @Override
    public void deleteUserStory(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User story not found"));

        // Check if the user story is in PENDING or VOTED status
        if (userStory.getStatus() == UserStoryStatus.PENDING || userStory.getStatus() == UserStoryStatus.VOTED) {
            userStoryRepository.delete(userStory);
        } else {
            throw new IllegalStateException("Cannot delete user story in " + userStory.getStatus() + " status");
        }
    }

    private PokerPlanningSession getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + id));
    }

}
