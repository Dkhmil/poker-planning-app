package com.khmil.management.mapper;

import com.khmil.management.web.model.response.MemberResponse;
import com.khmil.management.web.model.response.PokerPlanningSessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;

public interface SessionMapper {
    PokerPlanningSessionResponse toCreateSessionResponse(PokerPlanningSessionEntity session);

    MemberResponse toMemberResponse(MemberEntity memberEntity, PokerPlanningSessionEntity session);

    UserStoryResponse toUserStoryResponse(UserStoryEntity storyEntity);
}
