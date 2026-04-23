package com.gryphon.rxone.DTO;

import java.util.UUID;

import com.gryphon.rxone.enums.Difficulty;
import com.gryphon.rxone.enums.TestStatus;

import lombok.Data;

@Data
public class CreateTestDto {

    private String title;
    private int durationMins;
    private String discription;
    private int passMark;
    private Difficulty difficulty;
    private TestStatus status;
    private UUID createdById;
}