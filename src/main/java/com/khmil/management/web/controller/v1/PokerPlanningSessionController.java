package com.khmil.management.web.controller.v1;

import com.khmil.management.service.SessionService;
import com.khmil.management.web.model.request.AddUserStoryRequest;
import com.khmil.management.web.model.request.CreateSessionRequest;
import com.khmil.management.web.model.response.MemberResponse;
import com.khmil.management.web.model.response.PokerPlanningSessionResponse;
import com.khmil.management.web.model.response.UserStoryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions")
@Api("Poker Planning session API")
public class PokerPlanningSessionController {

    private final SessionService sessionService;

    @ApiOperation(value = "Create new Poker Planning session")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Poker Planning session created",
                    response = PokerPlanningSessionResponse.class),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PokerPlanningSessionResponse createSession(@RequestBody CreateSessionRequest sessionRequest) {
        return sessionService.createSession(sessionRequest);
    }

    @ApiOperation(value = "Join in the session")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Member added to the Poker Planning session created",
                    response = MemberResponse.class),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PostMapping("/{sessionId}/join")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse joinSession(@PathVariable String sessionId,
                                      @RequestParam String name) {
        return sessionService.joinSession(sessionId, name);
    }

    @ApiOperation(value = "Delete Poker Planning session by sessionId")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content - Poker Planning session deleted"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroySession(@RequestParam String sessionId) {
        sessionService.destroySession(sessionId);
    }


    @ApiOperation(value = "Join in the session")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "UserStory created",
                    response = UserStoryResponse.class),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PostMapping("/{sessionId}/stories")
    @ResponseStatus(HttpStatus.CREATED)
    public UserStoryResponse addUserStory(@PathVariable String sessionId, AddUserStoryRequest request) {
        return sessionService.addUserStory(sessionId, request);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content - User story successfully deleted"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @DeleteMapping("/{sessionId}/stories/{userStoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserStory(@PathVariable String sessionId, @PathVariable String userStoryId) {
        sessionService.deleteUserStory(sessionId, userStoryId);
    }

}
