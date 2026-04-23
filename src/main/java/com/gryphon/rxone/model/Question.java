package com.gryphon.rxone.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.gryphon.rxone.enums.Questiontype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Questiontype type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String prompt;
    
    private int marks;
    
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subjects subject;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topics topic;

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    private Subtopics subtopic;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "mcq_options", columnDefinition = "jsonb")
    private List<Map<String, Object>> mcqOptions;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}