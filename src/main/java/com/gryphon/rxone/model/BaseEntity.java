package com.gryphon.rxone.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}