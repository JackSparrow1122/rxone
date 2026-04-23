package com.gryphon.rxone.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gryphon.rxone.model.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

}