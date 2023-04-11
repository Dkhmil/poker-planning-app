package com.khmil.management.web.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PokerPlanningSessionResponse {
    private String invitationLink;
    private String title;
    private List<UserStoryResponse> userStories;
    private List<MemberResponse> members;
}
