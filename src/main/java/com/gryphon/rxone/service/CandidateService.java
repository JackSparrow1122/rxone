package com.gryphon.rxone.service;


import com.gryphon.rxone.DTO.Candidate.CreateCandidateRequest;
import com.gryphon.rxone.enums.Role;
import com.gryphon.rxone.model.Candidate;
import com.gryphon.rxone.model.Organisation;
import com.gryphon.rxone.model.User;
import com.gryphon.rxone.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private  final CandidateRepository candidateRepository;
    private final UserService userService;

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Transactional
    public Candidate createCandidate(CreateCandidateRequest request) {
        User user = userService.createUser(request.toCreateUserRequest(), Role.CANDIDATE);

        Organisation organisation = user.getOrganisation();

        Candidate candidate = Candidate.builder()
                .user(user)
                .organisation(organisation)
                .extraFields(request.getExtraFields())
                .lastUpdated(LocalDateTime.now())
                .build();

        return candidateRepository.save(candidate);
    }


}
