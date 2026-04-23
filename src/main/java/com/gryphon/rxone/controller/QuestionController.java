package com.gryphon.rxone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.DTO.CreateQuestionDto;
import com.gryphon.rxone.model.Question;
import com.gryphon.rxone.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public String createQuestion(@RequestBody CreateQuestionDto dto) {
        return questionService.createQuestion(dto);
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable UUID id) {
        return questionService.getQuestionById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable UUID id) {
        return questionService.deleteQuestion(id);
    }
    
    @GetMapping
    public List<Question> getAllQ() {
        return questionService.getAllQ();
    }
    
    
}