package com.gryphon.rxone.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.CreateQuestionDto;
import com.gryphon.rxone.enums.Questiontype;
import com.gryphon.rxone.model.Question;
import com.gryphon.rxone.model.Subjects;
import com.gryphon.rxone.model.Subtopics;
import com.gryphon.rxone.model.Topics;
import com.gryphon.rxone.repository.QuestionRepository;
import com.gryphon.rxone.repository.SubjectRepository;
import com.gryphon.rxone.repository.SubtopicRepository;
import com.gryphon.rxone.repository.TopicRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubtopicRepository subtopicRepository;

    public String createQuestion(CreateQuestionDto dto) {

        if (dto.getType() == Questiontype.MCQ &&
            (dto.getMcqOptions() == null || dto.getMcqOptions().isEmpty())) {
            return "MCQ options required";
        }

        if (dto.getType() == Questiontype.CODING && dto.getMcqOptions() != null && !dto.getMcqOptions().isEmpty()) {
            return "MCQ options should be empty for coding question";
        }

        Question question = new Question();
        question.setType(dto.getType());
        question.setPrompt(dto.getPrompt());
        question.setMcqOptions(dto.getMcqOptions());

        if (dto.getSubjectId() != null) {
            Optional<Subjects> subjectOptional = subjectRepository.findById(dto.getSubjectId());
            subjectOptional.ifPresent(question::setSubject);
        }

        if (dto.getTopicId() != null) {
            Optional<Topics> topicOptional = topicRepository.findById(dto.getTopicId());
            topicOptional.ifPresent(question::setTopic);
        }

        if (dto.getSubtopicId() != null) {
            Optional<Subtopics> subtopicOptional = subtopicRepository.findById(dto.getSubtopicId());
            subtopicOptional.ifPresent(question::setSubtopic);
        }

        questionRepository.save(question);
        return "Question created successfully";
    }

    public Question getQuestionById(UUID id) {
        return questionRepository.findById(id).orElse(null);
    }

    public String deleteQuestion(UUID id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isEmpty()) {
            return "Question not found";
        }

        questionRepository.delete(questionOptional.get());
        return "Question deleted successfully";
    }

	public List<Question> getAllQ() {
		
		return questionRepository.findAll();
	}
	
	// QuestionService (add these methods)

	public String updateQuestion(UUID id, CreateQuestionDto dto) {

	    Optional<Question> questionOptional = questionRepository.findById(id);

	    if (questionOptional.isEmpty()) {
	        return "Question not found";
	    }

	    if (dto.getType() == Questiontype.MCQ &&
	        (dto.getMcqOptions() == null || dto.getMcqOptions().isEmpty())) {
	        return "MCQ options required";
	    }

	    if (dto.getType() == Questiontype.CODING &&
	        dto.getMcqOptions() != null &&
	        !dto.getMcqOptions().isEmpty()) {
	        return "MCQ options should be empty for coding question";
	    }

	    Question question = questionOptional.get();

	    question.setType(dto.getType());
	    question.setPrompt(dto.getPrompt());
	    question.setMcqOptions(dto.getMcqOptions());

	    // Subject
	    if (dto.getSubjectId() != null) {
	        Optional<Subjects> subjectOptional =
	                subjectRepository.findById(dto.getSubjectId());

	        if (subjectOptional.isEmpty()) {
	            return "Subject not found";
	        }

	        question.setSubject(subjectOptional.get());
	    } else {
	        question.setSubject(null);
	    }

	    // Topic
	    if (dto.getTopicId() != null) {
	        Optional<Topics> topicOptional =
	                topicRepository.findById(dto.getTopicId());

	        if (topicOptional.isEmpty()) {
	            return "Topic not found";
	        }

	        question.setTopic(topicOptional.get());
	    } else {
	        question.setTopic(null);
	    }

	    // Subtopic
	    if (dto.getSubtopicId() != null) {
	        Optional<Subtopics> subtopicOptional =
	                subtopicRepository.findById(dto.getSubtopicId());

	        if (subtopicOptional.isEmpty()) {
	            return "Subtopic not found";
	        }

	        question.setSubtopic(subtopicOptional.get());
	    } else {
	        question.setSubtopic(null);
	    }

	    questionRepository.save(question);

	    return "Question updated successfully";
	}

	public String patchQuestion(UUID id, CreateQuestionDto dto) {

	    Optional<Question> questionOptional = questionRepository.findById(id);

	    if (questionOptional.isEmpty()) {
	        return "Question not found";
	    }

	    Question question = questionOptional.get();

	    if (dto.getType() != null) {
	        question.setType(dto.getType());
	    }

	    if (dto.getPrompt() != null) {
	        question.setPrompt(dto.getPrompt());
	    }

	    if (dto.getMcqOptions() != null) {
	        question.setMcqOptions(dto.getMcqOptions());
	    }

	    if (dto.getSubjectId() != null) {
	        Optional<Subjects> subjectOptional =
	                subjectRepository.findById(dto.getSubjectId());

	        if (subjectOptional.isEmpty()) {
	            return "Subject not found";
	        }

	        question.setSubject(subjectOptional.get());
	    }

	    if (dto.getTopicId() != null) {
	        Optional<Topics> topicOptional =
	                topicRepository.findById(dto.getTopicId());

	        if (topicOptional.isEmpty()) {
	            return "Topic not found";
	        }

	        question.setTopic(topicOptional.get());
	    }

	    if (dto.getSubtopicId() != null) {
	        Optional<Subtopics> subtopicOptional =
	                subtopicRepository.findById(dto.getSubtopicId());

	        if (subtopicOptional.isEmpty()) {
	            return "Subtopic not found";
	        }

	        question.setSubtopic(subtopicOptional.get());
	    }

	    // Final validation after patch
	    if (question.getType() == Questiontype.MCQ &&
	        (question.getMcqOptions() == null ||
	         question.getMcqOptions().isEmpty())) {
	        return "MCQ options required";
	    }

	    if (question.getType() == Questiontype.CODING &&
	        question.getMcqOptions() != null &&
	        !question.getMcqOptions().isEmpty()) {
	        return "MCQ options should be empty for coding question";
	    }

	    questionRepository.save(question);

	    return "Question patched successfully";
	}
}