package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.PokerPlanningSessionRepository;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.dal.repository.UserStoryRepository;
import com.khmil.management.dal.repository.VoteRepository;
import com.khmil.management.enums.DeckType;
import com.khmil.management.service.PokerPlanningSessionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PokerPlanningSessionServiceImpl implements PokerPlanningSessionService {

    private final PokerPlanningSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final UserStoryRepository userStoryRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public PokerPlanningSessionServiceImpl(PokerPlanningSessionRepository sessionRepository,
                                           UserRepository userRepository,
                                           UserStoryRepository userStoryRepository,
                                           VoteRepository voteRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.userStoryRepository = userStoryRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    @Transactional
    public PokerPlanningSession createSession(String title, DeckType deckType) {
        PokerPlanningSession session = new PokerPlanningSession();
        session.setTitle(title);
        session.setDeckType(deckType);
        return sessionRepository.save(session);
    }

    @Override
    @Transactional
    public PokerPlanningSession addUser(Long sessionId, Long userId) {
        PokerPlanningSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + sessionId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        session.getUsers().add(user);
        return sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
    @Override
    @Transactional
    public PokerPlanningSession removeUser(Long sessionId, Long userId) {
        PokerPlanningSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + sessionId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        session.getUsers().remove(user);
        return sessionRepository.save(session);
    }

    @Override
    @Transactional(readOnly = true)
    public PokerPlanningSession getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id " + id));
    }

    @Override
    @Transactional
    public PokerPlanningSession updateSession(PokerPlanningSession session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<PokerPlanningSession> getAllSessions() {
        return sessionRepository.findAll();
    }


}
