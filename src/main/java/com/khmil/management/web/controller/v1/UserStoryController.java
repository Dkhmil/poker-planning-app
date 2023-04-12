package com.khmil.management.web.controller.v1;

import com.khmil.management.service.UserStoryService;
import com.khmil.management.web.model.request.UserStoryRequest;
import com.khmil.management.web.model.request.VoteRequest;
import com.khmil.management.web.model.response.SessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;
import com.khmil.management.web.model.response.UserStoryVotationStatus;
import com.khmil.management.web.model.response.VoteResult;
import com.khmil.management.web.model.response.VoteSummary;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-stories")
public class UserStoryController {

    private final UserStoryService userStoryService;

    @ApiOperation(value = "Create User Story")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " User Story session created and added to the session",
                    response = SessionResponse.class),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserStoryResponse addUserStory(@RequestBody UserStoryRequest request) {
        return userStoryService.addUserStory(request);
    }

    @ApiOperation(value = "Get User Story by userStoryId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK  - User Story find"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @GetMapping("/{userStoryId}")
    @ResponseStatus(HttpStatus.OK)
    public UserStoryResponse getUserStory(@PathVariable Long userStoryId) {
        return userStoryService.getUserStory(userStoryId);
    }

    @ApiOperation(value = "Delete  User Story by userStoryId")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content - User Story deleted"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @DeleteMapping("/{userStoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserStory(@PathVariable Long userStoryId) {
        userStoryService.deleteUserStory(userStoryId);
    }

    @ApiOperation(value = "Start voting on User Story find by userStoryId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - User Story status changed to VOTING"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @GetMapping("/{userStoryId}/start")
    @ResponseStatus(HttpStatus.OK)
    public void startVoting(@PathVariable Long userStoryId) {
        userStoryService.startVoting(userStoryId);
    }

    @ApiOperation(value = "Start voting on User Story find by userStoryId")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created - user vote on user story accepted"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PutMapping("/{userStoryId}/vote")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResult submitVote(@PathVariable Long userStoryId, @RequestBody VoteRequest request) {
        return userStoryService.submitVote(userStoryId, request);
    }

    @ApiOperation(value = "Get User Story status by userStoryId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK  - User Story status retrieved"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @GetMapping("/{userStoryId}/status")
    @ResponseStatus(HttpStatus.OK)
    public UserStoryVotationStatus getUserStoryVotationStatus(@PathVariable Long userStoryId) {
        return userStoryService.getUserStoryVotationStatus(userStoryId);
    }

    @ApiOperation(value = "Stop voting for  User Story by userStoryId")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content - voting finished"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @DeleteMapping("/{userStoryId}/stop")
    public VoteSummary stopVoting(@PathVariable Long userStoryId) {
        return userStoryService.stopVoting(userStoryId);
    }
}
