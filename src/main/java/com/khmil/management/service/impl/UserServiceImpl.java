package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + name));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
