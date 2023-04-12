package com.khmil.management.web.controller.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khmil.management.service.PokerPlanningSessionService;
import com.khmil.management.web.model.request.SessionRequest;
import com.khmil.management.web.model.response.SessionResponse;
import com.khmil.management.web.model.response.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(PokerPlanningSessionController.class)
public class PokerPlanningSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokerPlanningSessionService sessionService;

    private static final String SESSION_ID = "1";
    private static final String SESSION_PATH = "/sessions/" + SESSION_ID;

    @Test
    public void testCreateSession() throws Exception {
        SessionRequest request = new SessionRequest();
        request.setUserName("John");
        request.setTitle("Planning session");

        SessionResponse response = SessionResponse.builder()
                .sessionId(1L)
                .title("Planning session")
                .build();

        given(sessionService.createSession(request)).willReturn(response);

        mockMvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sessionId", is(response.getSessionId().intValue())))
                .andExpect(jsonPath("$.title", is(response.getTitle())));
    }

    @Test
    public void testEnterSession() throws Exception {
        SessionRequest request = new SessionRequest();
        request.setUserName("Jane");
        request.setConfirmation(true);

        SessionResponse response = SessionResponse.builder()
                .sessionId(1L)
                .title("Planning session")
                .members(Arrays.asList(UserResponse.builder().name("John").build()))
                .build();

        given(sessionService.addUser(request)).willReturn(response);

        mockMvc.perform(put(SESSION_PATH + "/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sessionId", is(response.getSessionId().intValue())))
                .andExpect(jsonPath("$.title", is(response.getTitle())));
    }

    @Test
    public void testDestroySession() throws Exception {
        SessionRequest request = new SessionRequest();
        request.setSessionId(1L);

        mockMvc.perform(delete(SESSION_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetSessionById() throws Exception {
        SessionResponse response = SessionResponse.builder()
                .sessionId(1L)
                .title("Planning session")
                .build();

        given(sessionService.getSessionById(1L)).willReturn(response);

        mockMvc.perform(get(SESSION_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId", is(response.getSessionId().intValue())))
                .andExpect(jsonPath("$.title", is(response.getTitle())));
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(obj);
    }
}
