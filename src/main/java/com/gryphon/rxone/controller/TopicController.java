package com.gryphon.rxone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.DTO.CreateTopicDto;
import com.gryphon.rxone.model.Topics;
import com.gryphon.rxone.service.TopicService;

@RestController
@RequestMapping("/topics")
@CrossOrigin("*")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public String create(@RequestBody CreateTopicDto dto) {
        return topicService.create(dto);
    }

    @GetMapping
    public List<Topics> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/{id}")
    public Topics getTopicById(@PathVariable UUID id) {
        return topicService.getTopicById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTopic(@PathVariable UUID id) {
        return topicService.deleteTopic(id);
    }
}