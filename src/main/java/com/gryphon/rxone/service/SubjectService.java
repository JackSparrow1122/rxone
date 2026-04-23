package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.CreateSubjectDto;
import com.gryphon.rxone.model.Subjects;
import com.gryphon.rxone.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public String create(CreateSubjectDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return "Subject name is required";
        }

        Optional<Subjects> existingSubject =
                subjectRepository.findByNameIgnoreCase(dto.getName().trim());

        if (existingSubject.isPresent()) {
            return "Subject already exists";
        }

        Subjects subject = new Subjects();
        subject.setName(dto.getName().trim());

        subjectRepository.save(subject);
        return "Subject created successfully";
    }

    public List<Subjects> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subjects getSubjectById(UUID id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public String deleteSubject(UUID id) {
        Optional<Subjects> subjectOptional = subjectRepository.findById(id);

        if (subjectOptional.isEmpty()) {
            return "Subject not found";
        }

        subjectRepository.delete(subjectOptional.get());
        return "Subject deleted successfully";
    }
}