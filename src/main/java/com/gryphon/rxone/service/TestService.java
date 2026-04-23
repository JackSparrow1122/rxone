package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.CreateTestDto;
import com.gryphon.rxone.model.Tests;
import com.gryphon.rxone.model.Users;
import com.gryphon.rxone.repository.TestsRepository;
import com.gryphon.rxone.repository.UserRepository;

@Service
public class TestService {

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private UserRepository usersRepository;

    public String createTest(CreateTestDto dto) {
        Optional<Users> userOptional = usersRepository.findById(dto.getCreatedById());
        if (userOptional.isEmpty()) {
            return "User not found";
        }

        Tests test = new Tests();
        test.setCreatedBy(userOptional.get());
        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setDurationMins(dto.getDurationMins());
        test.setDifficulty(dto.getDifficulty());
        test.setInstructions(dto.getInstructions());
        test.setStatus(dto.getStatus());
        test.setPassMark(dto.getPassMark());

        testsRepository.save(test);

        return "Test created successfully";
    }

    public List<Tests> getAllTests() {
        return testsRepository.findAll();
    }

    public Tests getTestById(UUID id) {
        return testsRepository.findById(id).orElse(null);
    }

    public String deleteTest(UUID id) {
        Optional<Tests> testOptional = testsRepository.findById(id);
        if (testOptional.isEmpty()) {
            return "Test not found";
        }

        testsRepository.delete(testOptional.get());
        return "Test deleted successfully";
    }
}