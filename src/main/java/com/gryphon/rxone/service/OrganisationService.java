package com.gryphon.rxone.service;

import com.gryphon.rxone.DTO.Organisation.CreateOrganisationRequest;
import com.gryphon.rxone.DTO.Organisation.OrganisationResponse;
import com.gryphon.rxone.DTO.Organisation.UpdateOrganisationRequest;
import com.gryphon.rxone.model.Organisation;
import com.gryphon.rxone.repository.OrganisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganisationService {
    private final OrganisationRepository organisationRepository;

    public List<Organisation> getOrganisations() {return organisationRepository.findAll();}

    public Organisation getOrganisationById(UUID id){
        return organisationRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Organisation not found or ID is not provided")
        );
    }

    public Organisation createOrganisation(CreateOrganisationRequest request){
        if(organisationRepository.findByName(request.getName()).isPresent()){
            throw new RuntimeException("Organisation with the same name already exists");
        }
        Organisation organisation = Organisation.builder()
                .name(request.getName())
                .logoUrl(request.getLogoUrl())
                .build();
        return organisationRepository.save(organisation);
    }

    public Organisation updateOrganisation(UUID id, UpdateOrganisationRequest request){
        Organisation organisation = organisationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Organisation not found or ID is not provided")
        );
        organisation.setName(request.getName());
        organisation.setLogoUrl(request.getLogoUrl());
        return organisationRepository.save(organisation);
    }

    public String deleteOrganisation(UUID id){
        Organisation organisation = organisationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Organisation not found or ID is not provided")
        );
        organisationRepository.delete(organisation);
        return "Organisation deleted successfully";
    }

    public OrganisationResponse toOrganisationResponse(Organisation organisation) {
        return OrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .logoUrl(organisation.getLogoUrl())
                .createdAt(organisation.getCreatedAt())
                .updatedAt(organisation.getUpdatedAt())
                .build();
    }
}
