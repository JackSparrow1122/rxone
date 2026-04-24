package com.gryphon.rxone.DTO;

import java.util.Map;

import com.gryphon.rxone.enums.Difficulty;
import com.gryphon.rxone.enums.TestStatus;

import lombok.Data;

@Data
public class UpdateTestDto {

    private String title;
    private String description;
    private Integer durationMins;
    private Difficulty difficulty;
    private Map<String, Object> instructions;
    private TestStatus status;
    private Integer passMark;
}