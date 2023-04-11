package com.khmil.management.web.controller.v1;

import com.khmil.management.dal.entity.PokerPlanningSession;
import com.khmil.management.enums.DeckType;
import com.khmil.management.service.PokerPlanningSessionService;
import com.khmil.management.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions")
@Api("Poker Planning session API")
public class PokerPlanningSessionController {

    private final PokerPlanningSessionService sessionService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createSession(@RequestParam String title, @RequestParam DeckType deckType) {
        PokerPlanningSession session = sessionService.createSession(title, deckType);
        String link = String.format("/sessions/%s/enter", session.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("session_id", session.getId());
        response.put("link", link);
        return response;
    }

    @PostMapping("/{sessionId}/enter")
    public Map<String, Object> enterSession(@PathVariable Long sessionId, @RequestParam String name) {
        PokerPlanningSession session = sessionService.addUser(sessionId, name);
        Map<String, Object> response = new HashMap<>();
        response.put("title", session.getTitle());
        response.put("user_stories", session.getUserStories());
        response.put("members", session.getUsers());
        return response;
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroySession(@PathVariable Long sessionId, @RequestParam boolean confirmation) {
        if (confirmation) {
            sessionService.deleteSession(sessionId);
        } else {
            throw new RuntimeException("Confirmation required to destroy session");
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<PokerPlanningSession> getSessionById(@PathVariable Long sessionId) {
        PokerPlanningSession session = sessionService.getSessionById(sessionId);
        return ResponseEntity.ok(session);
    }

}
