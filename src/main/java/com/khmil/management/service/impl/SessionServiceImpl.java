package com.khmil.management.service.impl;

import com.khmil.management.enums.UserStoryStatus;
import com.khmil.management.dal.repository.MemberRepository;
import com.khmil.management.dal.repository.PokerPlanningSessionRepository;
import com.khmil.management.dal.repository.UserStoryRepository;
import com.khmil.management.exception.PokerPlanningSessionNotFoundException;
import com.khmil.management.exception.UserStoryNotFoundException;
import com.khmil.management.mapper.SessionMapper;
import com.khmil.management.service.SessionService;
import com.khmil.management.web.model.request.AddUserStoryRequest;
import com.khmil.management.web.model.request.CreateSessionRequest;
import com.khmil.management.web.model.response.MemberResponse;
import com.khmil.management.web.model.response.PokerPlanningSessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final PokerPlanningSessionRepository sessionRepository;

    private MemberRepository memberRepository;

    private UserStoryRepository userStoryRepository;

    private final SessionMapper sessionMapper;

    @Override
    @Transactional(readOnly = true)
    public PokerPlanningSessionResponse createSession(CreateSessionRequest request) {
        PokerPlanningSessionEntity session = PokerPlanningSessionEntity.builder()
                .title(request.getTitle())
                .inviteLink(UUID.randomUUID().toString())
                .deckType(request.getDeckType())
                .build();
        PokerPlanningSessionEntity savedSession = sessionRepository.save(session);
        return sessionMapper.toCreateSessionResponse(savedSession);
    }

    @Override
    @Transactional
    public MemberResponse joinSession(String inviteLink, String name) {
        PokerPlanningSessionEntity session = sessionRepository.findByInviteLink(inviteLink)
                .orElseThrow(() -> new PokerPlanningSessionNotFoundException(inviteLink));
        MemberEntity member = new MemberEntity();
        member.setName(name);
        session.getMembers().add(member);
        PokerPlanningSessionEntity savedSession = sessionRepository.save(session);
        return sessionMapper.toMemberResponse(member, savedSession);
    }

    @Override
    @Transactional
    public void destroySession(String inviteLink) {
        PokerPlanningSessionEntity session = sessionRepository.findByInviteLink(inviteLink)
                .orElseThrow(() -> new PokerPlanningSessionNotFoundException(inviteLink));
        sessionRepository.delete(session);
    }

    @Override
    public UserStoryResponse addUserStory(String sessionId, AddUserStoryRequest request) {
        PokerPlanningSessionEntity session = getSession(sessionId);
        UserStoryEntity storyEntity = UserStoryEntity.builder()
                .description(request.getDescription())
                .storyId(request.getId())
                .status(UserStoryStatus.PENDING)
                .session(session)
                .build();
        return sessionMapper.toUserStoryResponse(userStoryRepository.save(storyEntity));
    }

    @Override
    public void deleteUserStory(String sessionId, String userStoryId) {
        UserStoryEntity entity = getUserStory(userStoryId);
        if (entity.getStatus() != UserStoryStatus.PENDING
                || !entity.getSession().getSessionId().equals(sessionId)) {
            throw new UserStoryNotFoundException();
        }
        userStoryRepository.delete(entity);
    }

    @Override
    public UserStoryEntity getUserStory(String userStoryId) {
        return userStoryRepository.findById(userStoryId)
                .orElseThrow(UserStoryNotFoundException::new);
    }


    private PokerPlanningSessionEntity getSession(String sessionId) {
        return sessionRepository.findByInviteLink(sessionId)
                .orElseThrow(() -> new PokerPlanningSessionNotFoundException(sessionId));
    }


}
