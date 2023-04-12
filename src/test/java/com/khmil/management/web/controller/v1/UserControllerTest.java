package com.khmil.management.web.controller.v1;

import com.khmil.management.service.UserService;
import com.khmil.management.web.model.request.UserRequest;
import com.khmil.management.web.model.response.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setName("Alice");
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L)
                .name("Alice")
                .build();
        when(userService.createUser(request.getName())).thenReturn(expectedResponse);

        UserResponse response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getName(), response.getName());
        verify(userService, times(1)).createUser(request.getName());
    }

    @Test
    public void testGetUserByName() {
        String name = "Alice";
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L)
                .name(name)
                .build();
        when(userService.getUserByName(name)).thenReturn(expectedResponse);

        UserResponse response = userController.getUserByName(name);

        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getName(), response.getName());
        verify(userService, times(1)).getUserByName(name);
    }

    @Test
    public void testGetAllUsers() {
        List<UserResponse> expectedResponse = Arrays.asList(
                UserResponse.builder().id(1L).name("Alice").build(),
                UserResponse.builder().id(2L).name("Bob").build(),
                UserResponse.builder().id(3L).name("Charlie").build()
        );
        when(userService.getAllUsers()).thenReturn(expectedResponse);

        List<UserResponse> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(expectedResponse.size(), response.size());
        for (int i = 0; i < expectedResponse.size(); i++) {
            assertEquals(expectedResponse.get(i).getId(), response.get(i).getId());
            assertEquals(expectedResponse.get(i).getName(), response.get(i).getName());
        }
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        userController.deleteUser(userId);
        verify(userService, times(1)).deleteUser(userId);
    }
}
