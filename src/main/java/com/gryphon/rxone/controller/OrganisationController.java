package com.gryphon.rxone.controller;

import com.gryphon.rxone.DTO.*;
import com.gryphon.rxone.DTO.Organisation.CreateOrganisationRequest;
import com.gryphon.rxone.DTO.Organisation.OrganisationResponse;
import com.gryphon.rxone.DTO.Organisation.UpdateOrganisationRequest;
import com.gryphon.rxone.model.Organisation;
import com.gryphon.rxone.service.OrganisationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organisations")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class OrganisationController {
    private final OrganisationService organisationService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<Organisation>>> getOrganisations() {
        List<Organisation> organisations = organisationService.getOrganisations();
        return ResponseEntity.ok(BaseResponse.success(organisations));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<OrganisationResponse>> createOrganisation(@RequestBody @Valid CreateOrganisationRequest request){
        Organisation organisation = organisationService.createOrganisation(request);
        return ResponseEntity.ok(BaseResponse.success(organisationService.toOrganisationResponse(organisation)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Organisation>> getOrganisationById(@PathVariable UUID id) {
        Organisation organisation = organisationService.getOrganisationById(id);
        return ResponseEntity.ok(BaseResponse.success(organisation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<OrganisationResponse>> updateOrganisation(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateOrganisationRequest request
    ) {
        Organisation organisation = organisationService.updateOrganisation(id, request);
        return ResponseEntity.ok(BaseResponse.success(organisationService.toOrganisationResponse(organisation)));
    }
}
