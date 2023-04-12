package com.khmil.management.mapper;

import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.web.model.response.UserStoryResponse;
import org.springframework.stereotype.Component;

@Component
public class UserStoryMapper {
    public UserStoryResponse toUserStoryResponse(UserStory userStory) {
        return UserStoryResponse.builder()
                .id(userStory.getId())
                .description(userStory.getDescription())
                .votes(userStory.getVotes().size())
                .status(userStory.getStatus())
                .build();
    }
}
