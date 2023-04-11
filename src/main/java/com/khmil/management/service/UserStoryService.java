package com.khmil.management.service;

import com.khmil.management.web.model.request.AddUserStoryRequest;
import com.khmil.management.web.model.response.UserStoryResponse;

import java.util.List;

public interface UserStoryService {

    UserStoryResponse addUserStory(AddUserStoryRequest request);
    UserStoryResponse getUserStoryById(Long id);
    List<UserStoryResponse> getAllUserStories();
    void startVoting(Long id);
    void stopVoting(Long id);
}
