package com.khmil.management.service;

import com.khmil.management.dal.entity.User;

import java.util.List;

public interface UserService {
    User createUser(String name);
    User getUserByName(String name);
    List<User> getAllUsers();
    void deleteUser(Long userId);
}