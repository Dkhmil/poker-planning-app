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
import com.khmil.management.mapper.UserStoryMapper;
import com.khmil.management.service.UserStoryService;
import com.khmil.management.web.model.response.UserStoryVotationStatus;
import com.khmil.management.web.model.request.UserStoryRequest;
import com.khmil.management.web.model.request.VoteRequest;
import com.khmil.management.web.model.response.UserStoryResponse;
import com.khmil.management.web.model.response.VoteResult;
import com.khmil.management.web.model.response.VoteSummary;
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

    private final UserStoryMapper userStoryMapper;

    @Override
    @Transactional
    public void startVoting(Long userStoryId) {
        PokerPlanningSession session = getSessionByUserStoryId(userStoryId);
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));
        userStory.setStatus(UserStoryStatus.VOTING);
        userStoryRepository.save(userStory);
        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public VoteResult submitVote(Long userStoryId, VoteRequest request) {
        PokerPlanningSession session = getSessionByUserStoryId(userStoryId);
        if (session == null) {
            throw new ResourceNotFoundException(userStoryId);
        }
        User user = userRepository.findByName(request.getVoterName()).get();

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));

        if (!session.getUserStories().contains(userStory)) {
            throw new IllegalArgumentException("User story not found in session with id " + session.getId());
        }
        Vote vote = Vote.builder()
                .voteOption(request.getVoteOption())
                .userStory(userStory)
                .user(user)
                .build();
        if (userStory.getVotes().stream().map(v -> v.getUser().getName())
                .collect(Collectors.toSet())
                .contains(request.getVoterName())) {
            throw new RuntimeException("user already voted");
        }
        if (!userStory.getStatus().equals(UserStoryStatus.VOTING)) {
            throw new RuntimeException("Voting is not permitted");
        }
        userStory.getVotes().add(vote);
        userStoryRepository.save(userStory);
        sessionRepository.save(session);
        return VoteResult.builder()
                .voteCount(userStory.getVotes().size())
                .voters(userStory.getVotes().stream().map(v -> v.getUser().getName()).toList())
                .build();
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
    public VoteSummary stopVoting(Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException(userStoryId));

        if (userStory.getStatus() != UserStoryStatus.VOTING) {
            throw new IllegalStateException("User story is not in VOTING status");
        }
        userStory.setStatus(UserStoryStatus.VOTED);
        userStoryRepository.save(userStory);
        return new VoteSummary(userStory.getVotes());
    }


    @Override
    public UserStoryResponse addUserStory(UserStoryRequest request) {
        Long sessionId = request.getSessionId();
        String description = request.getDescription();
        PokerPlanningSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(sessionId));

        UserStory userStory = new UserStory();
        userStory.setDescription(description);
        userStory.setStatus(UserStoryStatus.PENDING);

        userStory.setSession(session);
        session.getUserStories().add(userStory);

        userStoryRepository.save(userStory);

        return userStoryMapper.toUserStoryResponse(userStory);
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

    @Override
    public UserStoryResponse getUserStory(Long id) {
        return userStoryMapper
                .toUserStoryResponse(userStoryRepository.findById(id)
                        .orElseThrow(EntityNotFoundException::new));
    }

    private PokerPlanningSession getSessionByUserStoryId(Long id) {
        UserStory userStory = userStoryRepository.findById(id).orElseThrow();
        return userStory.getSession();
    }
}
