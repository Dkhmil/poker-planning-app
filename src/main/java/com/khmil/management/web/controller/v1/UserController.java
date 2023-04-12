package com.khmil.management.web.controller.v1;

import com.khmil.management.service.UserService;
import com.khmil.management.web.model.request.UserRequest;
import com.khmil.management.web.model.response.UserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created - User successfully created"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error - server error")
    })
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest.getName());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a user by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - User found"),
            @ApiResponse(code = 404, message = "Not Found - User does not exist"),
            @ApiResponse(code = 500, message = "Internal error - server error")
    })
    public UserResponse getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Users found"),
            @ApiResponse(code = 500, message = "Internal error - server error")
    })
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content - User deleted"),
            @ApiResponse(code = 404, message = "Not Found - User does not exist"),
            @ApiResponse(code = 500, message = "Internal error - server error")
    })
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
