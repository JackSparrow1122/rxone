package com.gryphon.rxone.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateTestQuestionDto {
    private UUID testId;
    private UUID questionId;
    private int orderIndex;
    private int marks;
    private int timeLimitSecs;
}