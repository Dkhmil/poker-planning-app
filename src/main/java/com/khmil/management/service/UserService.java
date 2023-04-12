package com.khmil.management.service;

import com.khmil.management.web.model.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(String name);
    UserResponse getUserByName(String name);
    List<UserResponse> getAllUsers();
    void deleteUser(Long userId);
}