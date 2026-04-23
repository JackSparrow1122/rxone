package com.gryphon.rxone.DTO.Organisation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrganisationRequest {
    private String name;
    private String logoUrl;
}
