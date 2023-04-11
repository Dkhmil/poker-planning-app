package com.khmil.management.service;

import com.khmil.management.web.model.request.AddUserStoryRequest;
import com.khmil.management.web.model.response.MemberResponse;
import com.khmil.management.web.model.request.CreateSessionRequest;
import com.khmil.management.web.model.response.PokerPlanningSessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;

public interface SessionService {
    PokerPlanningSessionResponse createSession(CreateSessionRequest request);

    MemberResponse joinSession(String inviteLink, String name);

    void destroySession(String inviteLink);

    UserStoryResponse addUserStory(String sessionId, AddUserStoryRequest request);

    void deleteUserStory(String sessionId, String userStoryId);
    UserStoryEntity getUserStory(String userStoryId);
}
