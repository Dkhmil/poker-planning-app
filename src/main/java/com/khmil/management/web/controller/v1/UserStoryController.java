package com.khmil.management.web.controller.v1;

import com.khmil.management.dal.entity.UserStory;
import com.khmil.management.service.UserStoryService;
import com.khmil.management.web.model.UserStoryVotationStatus;
import com.khmil.management.web.model.response.VoteResult;
import com.khmil.management.web.model.response.VoteSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-stories")
public class UserStoryController {

    private final UserStoryService userStoryService;

    @PostMapping
    public ResponseEntity<UserStory> addUserStory(@RequestParam Long session_id,
                                                  @RequestParam String user_story_id,
                                                  @RequestParam String description) {
        UserStory userStory = userStoryService.addUserStory(session_id, user_story_id, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(userStory);
    }

    @DeleteMapping("/{userStoryId}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long userStoryId) {
        userStoryService.deleteUserStory(userStoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userStoryId}/start")
    public ResponseEntity<Void> startVoting(@PathVariable Long userStoryId) {
        userStoryService.startVoting(userStoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userStoryId}/vote")
    public ResponseEntity<VoteResult> submitVote(@PathVariable Long userStoryId,
                                                 @RequestParam Integer voteOption,
                                                 @RequestParam String voterName) {
        VoteResult voteResult = userStoryService.submitVote(userStoryId, voterName, voteOption);
        return ResponseEntity.ok(voteResult);
    }

    @GetMapping("/{userStoryId}/status")
    public ResponseEntity<UserStoryVotationStatus> getUserStoryVotationStatus(@PathVariable Long userStoryId) {
        UserStoryVotationStatus status = userStoryService.getUserStoryVotationStatus(userStoryId);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/{userStoryId}/stop")
    public ResponseEntity<VoteSummary> stopVoting(@PathVariable Long userStoryId) {
        VoteSummary summary = userStoryService.stopVoting(userStoryId);
        return ResponseEntity.ok(summary);
    }
}
