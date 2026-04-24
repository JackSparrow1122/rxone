package com.gryphon.rxone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.DTO.CreateTestDto;
import com.gryphon.rxone.DTO.UpdateTestDto;
import com.gryphon.rxone.model.Tests;
import com.gryphon.rxone.service.TestService;

@RestController
@CrossOrigin("*")
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/create")
    public String createTest(@RequestBody CreateTestDto dto) {
        return testService.createTest(dto);
    }

    @GetMapping("/all")
    public List<Tests> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public Tests getTestById(@PathVariable UUID id) {
        return testService.getTestById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTest(@PathVariable UUID id) {
        return testService.deleteTest(id);
    }

    @PutMapping("/update/{id}")
    public String updateTest(@PathVariable UUID id, @RequestBody UpdateTestDto dto) {
        return testService.updateTest(id, dto);
    }

    @PatchMapping("/patch/{id}")
    public String patchTest(@PathVariable UUID id, @RequestBody UpdateTestDto dto) {
        return testService.patchTest(id, dto);
    }
}