package com.gryphon.rxone.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateTopicDto {
    private String name;
    private UUID subjectId;
}