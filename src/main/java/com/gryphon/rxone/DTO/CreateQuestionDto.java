package com.gryphon.rxone.DTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gryphon.rxone.enums.Questiontype;

import lombok.Data;

@Data
public class CreateQuestionDto {

    private UUID subjectId;
    private UUID topicId;
    private UUID subtopicId;
    private Questiontype type;
    private String prompt;
    private List<Map<String, Object>> mcqOptions;
}