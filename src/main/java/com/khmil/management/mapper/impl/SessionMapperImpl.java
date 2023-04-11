package com.khmil.management.mapper.impl;

import com.khmil.management.mapper.SessionMapper;
import com.khmil.management.web.model.response.MemberResponse;
import com.khmil.management.web.model.response.PokerPlanningSessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements SessionMapper {
    @Override
    public PokerPlanningSessionResponse toCreateSessionResponse(PokerPlanningSessionEntity session) {
        return PokerPlanningSessionResponse.builder()
                .title(session.getTitle())
                .invitationLink(session.getInviteLink())
                .userStories(session.getUserStories().stream().map(this::toUserStoryResponse).toList())
                .members(session.getMembers().stream().map(this::toMember).toList())
                .build();
    }

    @Override
    public MemberResponse toMemberResponse(MemberEntity memberEntity, PokerPlanningSessionEntity session) {
        return MemberResponse.builder()
                .sessionTitle(session.getTitle())
                .name(memberEntity.getName())
                .id(memberEntity.getId())
                .build();
    }


    @Override
    public UserStoryResponse toUserStoryResponse(UserStoryEntity entity) {
        return UserStoryResponse.builder()
                .description(entity.getDescription())
                .votes(entity.getVotes().stream().map(VoteEntity::getValue).mapToInt(Integer::intValue).sum())
                .id(entity.getId())
                .build();
    }

    private MemberResponse toMember(MemberEntity entity) {
        return MemberResponse.builder()
                .id(entity.getId())
                .sessionTitle(entity.getSession().getTitle())
                .name(entity.getName())
                .build();
    }

}
