package com.gryphon.rxone.repository;

import com.gryphon.rxone.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface OrganisationRepository extends JpaRepository<Organisation, UUID>{
   Optional<Organisation> findByName(String name);
}
