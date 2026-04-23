package com.gryphon.rxone.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gryphon.rxone.model.Subtopics;

public interface SubtopicRepository extends JpaRepository<Subtopics, UUID> {
	
    Optional<Subtopics> findByNameIgnoreCase(String name);

}
