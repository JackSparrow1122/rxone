package com.gryphon.rxone.DTO;

import java.util.Map;
import java.util.UUID;

import com.gryphon.rxone.enums.Difficulty;
import com.gryphon.rxone.enums.TestStatus;

import lombok.Data;

@Data
public class CreateTestDto {

    private UUID createdById;
    private String title;
    private String description;
    private int durationMins;
    private Difficulty difficulty;
    private Map<String, Object> instructions;
    private TestStatus status;
    private int passMark;
}