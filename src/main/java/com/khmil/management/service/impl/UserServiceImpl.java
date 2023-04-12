package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.mapper.UserMapper;
import com.khmil.management.service.UserService;
import com.khmil.management.web.model.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(String name) {
        User user = new User();
        user.setName(name);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserByName(String name) {
        return userMapper.toUserResponse(userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + name)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
