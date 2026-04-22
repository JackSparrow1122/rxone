package com.gryphon.rxone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.model.Tests;
import com.gryphon.rxone.service.TestService;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/createTest")
    public String createTest(@RequestBody com.gryphon.rxone.DTO.CreateTestDto dto) {
        return testService.createTest(dto);
    }

    @GetMapping("/getAllTests")
    public List<Tests> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("getTest/{id}")
    public Tests getTestById(@PathVariable UUID id) {
        return testService.getTestById(id);
    }
}