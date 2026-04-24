package com.gryphon.rxone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.DTO.CreateSubtopicDto;
import com.gryphon.rxone.model.Subtopics;
import com.gryphon.rxone.service.SubtopicService;

@RestController
@RequestMapping("/subtopics")
@CrossOrigin("*")
public class SubtopicController {

    @Autowired
    private SubtopicService subtopicService;

    @PostMapping
    public String create(@RequestBody CreateSubtopicDto dto) {
        return subtopicService.create(dto);
    }

    @GetMapping
    public List<Subtopics> getAllSubtopics() {
        return subtopicService.getAllSubtopics();
    }

    @GetMapping("/{id}")
    public Subtopics getSubtopicById(@PathVariable UUID id) {
        return subtopicService.getSubtopicById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteSubtopic(@PathVariable UUID id) {
        return subtopicService.deleteSubtopic(id);
    }
}