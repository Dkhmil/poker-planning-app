package com.khmil.management.web.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khmil.management.enums.UserStoryStatus;
import com.khmil.management.service.UserStoryService;
import com.khmil.management.web.model.request.UserStoryRequest;
import com.khmil.management.web.model.request.VoteRequest;
import com.khmil.management.web.model.response.UserStoryResponse;
import com.khmil.management.web.model.response.VoteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserStoryControllerTest {

    private UserStoryController userStoryController;

    private UserStoryService userStoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userStoryService = mock(UserStoryService.class);
        userStoryController = new UserStoryController(userStoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(userStoryController).build();
    }

    @Test
    void shouldAddUserStory() throws Exception {
        // given
        UserStoryRequest request = UserStoryRequest.builder()
                .description("This is a test user story")
                .build();
        UserStoryResponse response = UserStoryResponse.builder()
                .id(1L)
                .description("This is a test user story")
                .status(UserStoryStatus.VOTING)
                .build();
        given(userStoryService.addUserStory(request)).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(post("/user-stories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("This is a test user story")))
                .andExpect(jsonPath("$.status", is("VOTING")));
    }

    @Test
    void shouldGetUserStory() throws Exception {
        // given
        UserStoryResponse response = UserStoryResponse.builder()
                .id(1L)
                .description("This is a test user story")
                .status(UserStoryStatus.VOTING)
                .build();
        given(userStoryService.getUserStory(1L)).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(get("/user-stories/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("This is a test user story")))
                .andExpect(jsonPath("$.status", is("VOTING")));
    }

    @Test
    void shouldDeleteUserStory() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/user-stories/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
        verify(userStoryService, times(1)).deleteUserStory(1L);
    }

    @Test
    void shouldStartVoting() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/user-stories/1/start")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
        verify(userStoryService, times(1)).startVoting(1L);
    }

    @Test
    void shouldSubmitVote() throws Exception {
        // given
        VoteRequest request = VoteRequest.builder()
                .voterName("John")
                .voteOption(3L)
                .build();
        VoteResult response = VoteResult.builder()
                .voteCount(1)
                .voters(Arrays.asList("John"))
                .summary("Vote submitted")
                .build();
        given(userStoryService.submitVote(1L, request)).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(put("/user-stories/1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voteCount", is(1)))
                .andExpect(jsonPath("$.voters[0]", is("John")))
                .andExpect(jsonPath("$.summary", is("Vote submitted")));
        verify(userStoryService, times(1)).submitVote(1L, request);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

