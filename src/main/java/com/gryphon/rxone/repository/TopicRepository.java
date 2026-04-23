package com.gryphon.rxone.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gryphon.rxone.model.Topics;

public interface TopicRepository extends JpaRepository<Topics, UUID> {
	
    Optional<Topics> findByNameIgnoreCase(String name);

}
