package com.gryphon.rxone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.DTO.CreateSubjectDto;
import com.gryphon.rxone.model.Subjects;
import com.gryphon.rxone.service.SubjectService;

@RestController
@RequestMapping("/subjects")
@CrossOrigin("*")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public String create(@RequestBody CreateSubjectDto dto) {
        return subjectService.create(dto);
    }

    @GetMapping
    public List<Subjects> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Subjects getSubjectById(@PathVariable UUID id) {
        return subjectService.getSubjectById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable UUID id) {
        return subjectService.deleteSubject(id);
    }
}