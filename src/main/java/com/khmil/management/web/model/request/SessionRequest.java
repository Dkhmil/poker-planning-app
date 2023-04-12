package com.khmil.management.web.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.khmil.management.enums.DeckType;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionRequest {

    Long sessionId;

    String userName;

    boolean confirmation;

    String title;

    DeckType deckType;
}
