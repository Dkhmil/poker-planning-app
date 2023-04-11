package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User createUser(String name) {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
