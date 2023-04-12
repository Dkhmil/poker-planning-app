package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.PokerPlanningSessionRepository;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.enums.DeckType;
import com.khmil.management.mapper.UserMapper;
import com.khmil.management.mapper.UserStoryMapper;
import com.khmil.management.web.model.request.SessionRequest;
import com.khmil.management.web.model.response.SessionResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class PokerPlanningSessionServiceImplTest {

    @Mock
    private PokerPlanningSessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserStoryMapper userStoryMapper;

    @InjectMocks
    private PokerPlanningSessionServiceImpl sessionService;

    @Test
    public void createSession_shouldCreateAndReturnNewSession() {
        // Given
        SessionRequest request = new SessionRequest();
        request.setTitle("Test Session");
        request.setDeckType(DeckType.FIBONACCI);

        PokerPlanningSession session = new PokerPlanningSession();
        session.setId(1L);
        session.setTitle(request.getTitle());
        session.setDeckType(request.getDeckType());
        session.setLink("/sessions/1234567/enter");

        given(sessionRepository.save(any(PokerPlanningSession.class))).willReturn(session);

        // When
        SessionResponse result = sessionService.createSession(request);

        // Then
        assertEquals(result.getSessionId(), session.getId());
        assertEquals(result.getTitle(), session.getTitle());
        assertEquals(result.getLink(), session.getLink());
        assertEquals(result.getMembers().size(), session.getUsers().size());
    }

    @Test
    public void addUser_shouldAddUserToSessionAndReturnUpdatedSession() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setName("John");

        PokerPlanningSession session = new PokerPlanningSession();
        session.setId(1L);
        session.setTitle("Test Session");
        session.getUsers().add(user);

        SessionRequest request = new SessionRequest();
        request.setSessionId(1L);
        request.setUserName(user.getName());

        given(sessionRepository.findById(any())).willReturn(Optional.of(session));
        given(sessionRepository.save(any())).willReturn(session);
        given(userRepository.findByName(request.getUserName())).willReturn(Optional.of(user));

        // When
        SessionResponse result = sessionService.addUser(request);

        // Then
        assertEquals(result.getSessionId(), session.getId());
        assertEquals(result.getTitle(), session.getTitle());
        assertEquals(result.getLink(), session.getLink());
        assertEquals(result.getMembers().size(), session.getUsers().size());
    }

    @Test
    public void addUser_shouldThrowEntityNotFoundExceptionWhenSessionNotFound() {
        // Given
        SessionRequest request = new SessionRequest();
        request.setSessionId(1L);
        request.setUserName("John");

        given(sessionRepository.findById(request.getSessionId())).willReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> sessionService.addUser(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Session not found with id " + request.getSessionId());
    }

    @Test
    public void addUser_shouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        // Given
        PokerPlanningSession session = new PokerPlanningSession();
        session.setId(1L);

        SessionRequest request = new SessionRequest();
        request.setSessionId(session.getId());
        request.setUserName("John");

        given(sessionRepository.findById(request.getSessionId())).willReturn(Optional.of(session));
        given(userRepository.findByName(request.getUserName())).willReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> sessionService.addUser(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found with id " + request.getUserName());
    }
}
