package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.CreateTopicDto;
import com.gryphon.rxone.model.Subjects;
import com.gryphon.rxone.model.Topics;
import com.gryphon.rxone.repository.SubjectRepository;
import com.gryphon.rxone.repository.TopicRepository;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public String create(CreateTopicDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return "Topic name is required";
        }

        if (dto.getSubjectId() == null) {
            return "Subject id is required";
        }

        Optional<Subjects> subjectOptional = subjectRepository.findById(dto.getSubjectId());
        if (subjectOptional.isEmpty()) {
            return "Subject not found";
        }

        Optional<Topics> existingTopic = topicRepository.findByNameIgnoreCase(dto.getName().trim());
        if (existingTopic.isPresent()) {
            return "Topic already exists";
        }

        Topics topic = new Topics();
        topic.setName(dto.getName().trim());
        topic.setSubject(subjectOptional.get());

        topicRepository.save(topic);
        return "Topic created successfully";
    }

    public List<Topics> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topics getTopicById(UUID id) {
        return topicRepository.findById(id).orElse(null);
    }

    public String deleteTopic(UUID id) {
        Optional<Topics> topicOptional = topicRepository.findById(id);

        if (topicOptional.isEmpty()) {
            return "Topic not found";
        }

        topicRepository.delete(topicOptional.get());
        return "Topic deleted successfully";
    }
}