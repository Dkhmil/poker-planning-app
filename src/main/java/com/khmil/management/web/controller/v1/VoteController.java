package com.khmil.management.web.controller.v1;

import com.khmil.management.dal.entity.UserStoryEntity;
import com.khmil.management.dal.entity.VoteEntity;
import com.khmil.management.enums.UserStoryStatus;
import com.khmil.management.mapper.UserStoryService;
import com.khmil.management.mapper.VoteService;
import com.khmil.management.service.impl.SessionServiceImpl;
import com.khmil.management.web.model.VoteRequest;
import com.khmil.management.web.model.response.UserStoryResponse;
import com.khmil.management.web.model.response.VoteResponse;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/votes")
@Api("Vote API")
public class VoteController {

    private final SessionServiceImpl sessionService;
    private final UserStoryService userStoryService;
    private final VoteService voteService;

    @PostMapping("/{userStoryId}/start")
    @ResponseStatus(HttpStatus.OK)
    public void startVoting(@PathVariable Long userStoryId) {
        UserStoryEntity userStory = sessionService.getUserStory(String.valueOf(userStoryId));
        if (userStory.getStatus() == UserStoryStatus.PENDING || userStory.getStatus() == UserStoryStatus.VOTED) {
            userStoryService.startVoting(userStoryId);
        }
    }

    @PostMapping("/{userStoryId}/vote")
    public void vote(@PathVariable Long userStoryId, @RequestBody VoteRequest voteRequest) {
        UserStoryResponse userStory = userStoryService.getUserStoryById(userStoryId);
        if (userStory.getStatus() == UserStoryStatus.VOTING) {
            VoteResponse vote = voteService.vote(userStoryId, voteRequest.getVoterName(), voteRequest.getVoteValue());
        }
    }

    @GetMapping("/{userStoryId}/status")
    public ResponseEntity<?> getVotationStatus(@PathVariable Long userStoryId) {
        UserStoryResponse userStory = userStoryService.getUserStoryById(userStoryId);
        if (userStory.getStatus() == UserStoryStatus.VOTING) {
            List<VoteEntity> votes = voteService.getVotesByUserStoryId(userStoryId);
            VotationStatusResponse response = new VotationStatusResponse(userStoryId, userStory.getTitle(), votes);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Cannot get the votation status of a user story that is not currently being voted on");
        }
    }

    @PostMapping("/{userStoryId}/stop")
    public ResponseEntity<?> stopVoting(@PathVariable Long userStoryId) {
        UserStoryResponse userStory = userStoryService.getUserStoryById(userStoryId);
        if (userStory.getStatus() == UserStoryStatus.VOTING) {
            userStoryService.stopVoting(userStoryId);
            List<VoteEntity> votes = voteService.getVotesByUserStoryId(userStoryId);
            VotationResultsResponse response = new VotationResultsResponse(userStoryId, userStory.getTitle(), votes);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Cannot stop voting on a user story that is not currently being voted on");
        }
    }
}
