package com.khmil.management.mapper;

import com.khmil.management.dal.entity.MemberEntity;
import com.khmil.management.dal.entity.PokerPlanningSessionEntity;
import com.khmil.management.dal.entity.UserStoryEntity;
import com.khmil.management.web.model.response.MemberResponse;
import com.khmil.management.web.model.response.PokerPlanningSessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;

public interface SessionMapper {
    PokerPlanningSessionResponse toCreateSessionResponse(PokerPlanningSessionEntity session);

    MemberResponse toMemberResponse(MemberEntity memberEntity, PokerPlanningSessionEntity session);

    UserStoryResponse toUserStoryResponse(UserStoryEntity storyEntity);
}
