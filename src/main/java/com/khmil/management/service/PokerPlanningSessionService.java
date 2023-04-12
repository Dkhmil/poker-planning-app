package com.khmil.management.service;

import com.khmil.management.web.model.request.SessionRequest;
import com.khmil.management.web.model.response.SessionResponse;

public interface PokerPlanningSessionService {
    SessionResponse createSession(SessionRequest request);
    SessionResponse addUser(SessionRequest request);
    SessionResponse getSessionById(Long id);
    void deleteSession(SessionRequest request);


}
