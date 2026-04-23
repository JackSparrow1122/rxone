package com.gryphon.rxone.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gryphon.rxone.model.Tests;

public interface TestsRepository extends JpaRepository<Tests, UUID> {

}
