package com.gryphon.rxone.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gryphon.rxone.model.Subjects;

public interface SubjectRepository extends JpaRepository<Subjects, UUID>{
    Optional<Subjects> findByNameIgnoreCase(String name);

}
