package com.khmil.management.service.impl;

import com.khmil.management.dal.entity.User;
import com.khmil.management.dal.repository.UserRepository;
import com.khmil.management.mapper.UserMapper;
import com.khmil.management.web.model.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        String userName = "John";
        User user = User.builder().name(userName).build();
        UserResponse expectedResponse = UserResponse.builder().id(1L).name(userName).build();

        given(userRepository.save(any())).willReturn(user);
        given(userMapper.toUserResponse(any())).willReturn(expectedResponse);

        UserResponse actualResponse = userService.createUser(userName);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetUserByName() {
        String userName = "John";
        User user = User.builder().id(1L).name(userName).build();
        UserResponse expectedResponse = UserResponse.builder().id(1L).name(userName).build();

        given(userRepository.findByName(userName)).willReturn(Optional.of(user));
        given(userMapper.toUserResponse(user)).willReturn(expectedResponse);

        UserResponse actualResponse = userService.getUserByName(userName);

        assertEquals(actualResponse, expectedResponse);
        verify(userRepository).findByName(userName);
        verify(userMapper).toUserResponse(user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByNameNotFound() {
        String userName = "John";

        given(userRepository.findByName(userName)).willReturn(Optional.empty());

        userService.getUserByName(userName);

    }

    @Test
    public void testGetAllUsers() {

        User user1 = User.builder().id(1L).name("John").build();
        User user2 = User.builder().id(2L).name("Jane").build();
        List<User> users = List.of(user1, user2);

        UserResponse userResponse1 = UserResponse.builder().id(1L).name("John").build();
        UserResponse userResponse2 = UserResponse.builder().id(2L).name("Jane").build();
        List<UserResponse> expectedResponses = List.of(userResponse1, userResponse2);

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.toUserResponse(user1)).willReturn(userResponse1);
        given(userMapper.toUserResponse(user2)).willReturn(userResponse2);


        List<UserResponse> actualResponses = userService.getAllUsers();

        assertEquals(actualResponses, expectedResponses);
        verify(userRepository).findAll();
        verify(userMapper).toUserResponse(user1);
        verify(userMapper).toUserResponse(user2);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        userService.deleteUser(userId);
        verify(userRepository).deleteById(userId);
    }
}

