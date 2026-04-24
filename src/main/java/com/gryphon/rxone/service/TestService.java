package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gryphon.rxone.DTO.CreateTestDto;
import com.gryphon.rxone.DTO.UpdateTestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.model.Tests;
import com.gryphon.rxone.model.User;
import com.gryphon.rxone.repository.TestsRepository;
import com.gryphon.rxone.repository.UserRepository;

@Service
public class TestService {

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private UserRepository usersRepository;

    public String createTest(CreateTestDto dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> userOptional = usersRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "Logged in user not found";
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

    public String updateTest(UUID id, UpdateTestDto dto) {
        Optional<Tests> testOptional = testsRepository.findById(id);

        if (testOptional.isEmpty()) {
            return "Test not found";
        }

        Tests test = testOptional.get();

        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setDurationMins(dto.getDurationMins());
        test.setDifficulty(dto.getDifficulty());
        test.setInstructions(dto.getInstructions());
        test.setStatus(dto.getStatus());
        test.setPassMark(dto.getPassMark());

        testsRepository.save(test);

        return "Test updated successfully";
    }

    public String patchTest(UUID id, UpdateTestDto dto) {
        Optional<Tests> testOptional = testsRepository.findById(id);

        if (testOptional.isEmpty()) {
            return "Test not found";
        }

        Tests test = testOptional.get();

        if (dto.getTitle() != null) {
            test.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            test.setDescription(dto.getDescription());
        }

        if (dto.getDurationMins() != null) {
            test.setDurationMins(dto.getDurationMins());
        }

        if (dto.getDifficulty() != null) {
            test.setDifficulty(dto.getDifficulty());
        }

        if (dto.getInstructions() != null) {
            test.setInstructions(dto.getInstructions());
        }

        if (dto.getStatus() != null) {
            test.setStatus(dto.getStatus());
        }

        if (dto.getPassMark() != null) {
            test.setPassMark(dto.getPassMark());
        }

        testsRepository.save(test);

        return "Test patched successfully";
    }
}