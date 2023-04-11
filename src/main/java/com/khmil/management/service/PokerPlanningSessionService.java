package com.khmil.management.service;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.enums.DeckType;

import java.util.List;

public interface PokerPlanningSessionService {
    PokerPlanningSession createSession(String title, DeckType deckType); // +
    PokerPlanningSession addUser(Long sessionId, Long userId);     // +
    PokerPlanningSession removeUser(Long sessionId, Long userId);
    PokerPlanningSession getSessionById(Long id);
    PokerPlanningSession updateSession(PokerPlanningSession session);
    void deleteSession(Long id); // +
    // Submit a vote for a user story

    List<PokerPlanningSession> getAllSessions();

}
