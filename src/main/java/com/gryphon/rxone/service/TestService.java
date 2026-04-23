package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gryphon.rxone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.model.Tests;
import com.gryphon.rxone.repository.TestsRepository;
import com.gryphon.rxone.repository.UserRepository;

@Service
public class TestService {

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private UserRepository usersRepository;

    public String createTest(com.gryphon.rxone.DTO.CreateTestDto dto) {
    	Optional<User> userOptional = usersRepository.findById(dto.getCreatedById());
        if (userOptional.isEmpty()) {
            return "User not found";
        }

        Tests test = new Tests();
        test.setTitle(dto.getTitle());
        test.setDurationMins(dto.getDurationMins());
        test.setPassMark(dto.getPassMark());
        test.setDifficulty(dto.getDifficulty());
        test.setStatus(dto.getStatus());
        test.setCreatedBy(userOptional.get());

        testsRepository.save(test);

        return "Test created successfully";
    }

    public List<Tests> getAllTests() {
        return testsRepository.findAll();
    }

    public Tests getTestById(UUID id) {
        return testsRepository.findById(id).orElse(null);
    }
}