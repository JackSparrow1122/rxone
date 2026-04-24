package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.CreateSubtopicDto;
import com.gryphon.rxone.model.Subjects;
import com.gryphon.rxone.model.Subtopics;
import com.gryphon.rxone.model.Topics;
import com.gryphon.rxone.repository.SubjectRepository;
import com.gryphon.rxone.repository.SubtopicRepository;
import com.gryphon.rxone.repository.TopicRepository;

@Service
public class SubtopicService {

    @Autowired
    private SubtopicRepository subtopicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    public String create(CreateSubtopicDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return "Subtopic name is required";
        }

        if (dto.getSubjectId() == null) {
            return "Subject id is required";
        }

        if (dto.getTopicId() == null) {
            return "Topic id is required";
        }

        Optional<Subjects> subjectOptional = subjectRepository.findById(dto.getSubjectId());
        if (subjectOptional.isEmpty()) {
            return "Subject not found";
        }

        Optional<Topics> topicOptional = topicRepository.findById(dto.getTopicId());
        if (topicOptional.isEmpty()) {
            return "Topic not found";
        }

        Optional<Subtopics> existingSubtopic =
                subtopicRepository.findByNameIgnoreCase(dto.getName().trim());

        if (existingSubtopic.isPresent()) {
            return "Subtopic already exists";
        }

        Subtopics subtopic = new Subtopics();
        subtopic.setName(dto.getName().trim());
        subtopic.setSubject(subjectOptional.get());
        subtopic.setTopic(topicOptional.get());

        subtopicRepository.save(subtopic);
        return "Subtopic created successfully";
    }

    public List<Subtopics> getAllSubtopics() {
        return subtopicRepository.findAll();
    }

    public Subtopics getSubtopicById(UUID id) {
        return subtopicRepository.findById(id).orElse(null);
    }

    public String deleteSubtopic(UUID id) {
        Optional<Subtopics> subtopicOptional = subtopicRepository.findById(id);

        if (subtopicOptional.isEmpty()) {
            return "Subtopic not found";
        }

        subtopicRepository.delete(subtopicOptional.get());
        return "Subtopic deleted successfully";
    }
}