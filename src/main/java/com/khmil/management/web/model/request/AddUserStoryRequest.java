package com.khmil.management.web.model.request;

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
public class AddUserStoryRequest {
    @NotNull(message = "Id type is required")
    private Long id;
    @NotBlank(message = "Description is required")
    private String description;
}
