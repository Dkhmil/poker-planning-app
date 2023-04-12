package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.dal.repository.PokerPlanningSessionRepository;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.dal.repository.UserStoryRepository;
import com.khmil.management.dal.repository.VoteRepository;
import com.khmil.management.enums.UserStoryStatus;
import com.khmil.management.exception.ResourceNotFoundException;
import com.khmil.management.mapper.UserStoryMapper;
import com.khmil.management.web.model.request.VoteRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore// tests should be fixed
public class UserStoryServiceImplTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private PokerPlanningSessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserStoryMapper userStoryMapper;

    @InjectMocks
    private UserStoryServiceImpl userStoryService;

    @Test
    public void startVoting_shouldUpdateUserStoryAndSession() {
        Long userStoryId = 1L;
        UserStory userStory = new UserStory();
        PokerPlanningSession session = new PokerPlanningSession();
        session.setUserStories(Collections.singletonList(userStory));

        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(sessionRepository.save(session)).thenReturn(session);

        userStoryService.startVoting(userStoryId);

        verify(userStoryRepository).findById(userStoryId);
        verify(userStoryRepository).save(userStory);
        verify(sessionRepository).save(session);
        assertEquals(UserStoryStatus.VOTING, userStory.getStatus());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void startVoting_withInvalidUserStory_shouldThrowResourceNotFoundException() {
        Long userStoryId = 1L;
        UserStory userStory = new UserStory();
        PokerPlanningSession session = new PokerPlanningSession();
        session.setUserStories(Collections.singletonList(userStory));

        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.empty());
        userStoryService.startVoting(userStoryId);

    }

    @Test
    public void submitVote_shouldAddVoteAndUpdateUserStoryAndSession() {
        Long userStoryId = 1L;
        String voterName = "John";
        UserStory userStory = new UserStory();
        PokerPlanningSession session = new PokerPlanningSession();
        session.setUserStories(Collections.singletonList(userStory));
        User user = new User();
        user.setName(voterName);
        when(userRepository.findByName(voterName)).thenReturn(Optional.of(user));
        when(userStoryRepository.findById(userStoryId)).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(sessionRepository.save(session)).thenReturn(session);

        userStoryService.submitVote(userStoryId, new VoteRequest(voterName, 1L));

        verify(userRepository).findByName(voterName);
        verify(userStoryRepository).findById(userStoryId);
        verify(userStoryRepository).save(userStory);
        verify(sessionRepository).save(session);
        assertEquals(1, userStory.getVotes().size());
        assertEquals(user, userStory.getVotes().get(0).getUser());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void submitVote_withInvalidUserStory_shouldThrowResourceNotFoundException() {
        Long userStoryId = 1L;
        String voterName = "John";
        userStoryService.submitVote(userStoryId, new VoteRequest(voterName, 1L));
    }
}