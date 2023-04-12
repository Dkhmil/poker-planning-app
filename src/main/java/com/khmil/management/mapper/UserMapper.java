package com.khmil.management.mapper;

import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.entity.Vote;
import com.khmil.management.web.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .sessionId(user.getSession().getId())
                .voteIds(user.getVotes().stream().map(Vote::getId).toList())
                .name(user.getName())
                .build();
    }
}
