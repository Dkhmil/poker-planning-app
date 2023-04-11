package com.khmil.management.mapper.impl;

import com.khmil.management.mapper.UserStoryService;
import com.khmil.management.web.model.request.AddUserStoryRequest;
import com.khmil.management.web.model.response.UserStoryResponse;

import java.util.List;

public class UserStoryServiceImpl implements UserStoryService {
    @Override
    public UserStoryResponse addUserStory(AddUserStoryRequest request) {
        return null;
    }

    @Override
    public UserStoryResponse getUserStoryById(Long id) {
        return null;
    }

    @Override
    public List<UserStoryResponse> getAllUserStories() {
        return null;
    }

    @Override
    public void startVoting(Long id) {

    }

    @Override
    public void stopVoting(Long id) {

    }
}
