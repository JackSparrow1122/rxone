package com.gryphon.rxone.DTO.Organisation;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateOrganisationRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String logoUrl;
}
