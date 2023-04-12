package com.khmil.management.web.controller.v1;

import com.khmil.management.service.PokerPlanningSessionService;
import com.khmil.management.web.model.request.SessionRequest;
import com.khmil.management.web.model.response.SessionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/sessions")
@Api("Poker Planning session API")
public class PokerPlanningSessionController {

    private final PokerPlanningSessionService sessionService;

    @ApiOperation(value = "Create new Poker Planning session")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Poker Planning session created",
                    response = SessionResponse.class),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@RequestBody SessionRequest request) {
        return sessionService.createSession(request);
    }

    @ApiOperation(value = "Join in the session")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Member added to the Poker Planning session created",
                    response = SessionResponse.class),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @PutMapping("/{sessionId}/enter")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse enterSession(@RequestBody SessionRequest request) {
        return sessionService.addUser(request);
    }

    @ApiOperation(value = "Delete Poker Planning session by sessionId")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content - Poker Planning session deleted"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroySession(@RequestBody SessionRequest request) {
        sessionService.deleteSession(request);
    }

    @ApiOperation(value = "Get Poker Planning session by sessionId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK  - Poker Planning session find"),
            @ApiResponse(code = 400, message = "Bad request - client error"),
            @ApiResponse(code = 404, message = "Not Found - Poker Planning session does not exist"),
            @ApiResponse(code = 500, message = "Internal error -server error")
    })
    @GetMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public SessionResponse getSessionById(@PathVariable Long sessionId) {
        return sessionService.getSessionById(sessionId);
    }

}
