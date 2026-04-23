package com.gryphon.rxone.controller;

import com.gryphon.rxone.DTO.BaseResponse;
import com.gryphon.rxone.DTO.Candidate.CreateCandidateRequest;
import com.gryphon.rxone.model.Candidate;
import com.gryphon.rxone.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping("/")
    public ResponseEntity<BaseResponse<String>> createCandidate(@RequestBody @Valid CreateCandidateRequest request
    ) {
        Candidate candidate = candidateService.createCandidate(request);
        return ResponseEntity.ok(
                BaseResponse.success( "Candidate created successfully with ID: " + candidate.getId())
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Candidate>>> getCandidates(){
        return ResponseEntity.ok(BaseResponse.success(candidateService.getAllCandidates()));
    }

}
