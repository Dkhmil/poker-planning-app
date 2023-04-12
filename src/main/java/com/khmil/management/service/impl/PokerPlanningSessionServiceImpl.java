package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.PokerPlanningSessionRepository;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.exception.ConfirmationException;
import com.khmil.management.mapper.UserMapper;
import com.khmil.management.mapper.UserStoryMapper;
import com.khmil.management.service.PokerPlanningSessionService;
import com.khmil.management.web.model.request.SessionRequest;
import com.khmil.management.web.model.response.SessionResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PokerPlanningSessionServiceImpl implements PokerPlanningSessionService {

    private final PokerPlanningSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserStoryMapper userStoryMapper;

    @Override
    @Transactional
    public SessionResponse createSession(SessionRequest request) {
        PokerPlanningSession session = new PokerPlanningSession();
        session.setTitle(request.getTitle());
        session.setDeckType(request.getDeckType());
        session.setLink(String.format("/sessions/%s/enter", (int) (Math.random() * 10000000) + 1));
        PokerPlanningSession persisted = sessionRepository.save(session);
        return getSessionResponse(persisted);
    }

    @Override
    @Transactional
    public SessionResponse addUser(SessionRequest request) {
        PokerPlanningSession session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + request.getSessionId()));
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + request.getUserName()));
        session.getUsers().add(user);
        PokerPlanningSession persisted = sessionRepository.save(session);
        return getSessionResponse(persisted);
    }

    @Override
    @Transactional
    public void deleteSession(SessionRequest request) {
        if (request.isConfirmation() && Objects.nonNull(getSessionById(request.getSessionId()))) {
            sessionRepository.deleteById(request.getSessionId());
        } else {
            throw new ConfirmationException(request.getSessionId());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public SessionResponse getSessionById(Long id) {
        PokerPlanningSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + id));
        return getSessionResponse(session);
    }

    private SessionResponse getSessionResponse(PokerPlanningSession session) {
        return SessionResponse
                .builder()
                .link(session.getLink())
                .title(session.getTitle())
                .userStories(session.getUserStories().stream().map(userStoryMapper::toUserStoryResponse).toList())
                .members(session.getUsers().stream().map(userMapper::toUserResponse).toList())
                .sessionId(session.getId())
                .build();
    }
}
