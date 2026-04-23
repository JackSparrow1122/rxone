package com.gryphon.rxone.repository;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gryphon.rxone.model.Question;
import com.gryphon.rxone.model.Tests;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "test_questions",
    uniqueConstraints = @UniqueConstraint(columnNames = {"test_id", "question_id"})
)
public class TestQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    @JsonIgnore
    private Tests test;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @Column(nullable = false)
    private int marks;

    @Column(name = "time_limit_secs", nullable = false)
    private int timeLimitSecs;
}