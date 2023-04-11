package com.khmil.management.web.model.request;

import com.khmil.management.enums.DeckType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Deck type is required")
    private DeckType deckType;
}
