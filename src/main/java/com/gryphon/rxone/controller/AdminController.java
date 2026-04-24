package com.gryphon.rxone.controller;


import com.gryphon.rxone.DTO.BaseResponse;
import com.gryphon.rxone.DTO.User.CreateUserRequest;
import com.gryphon.rxone.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.model.User;
import com.gryphon.rxone.service.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERADMIN')")
public class AdminController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<BaseResponse<String>> createUser(
            @RequestBody @Valid CreateUserRequest request,
            @RequestParam Role role
    ) {
        User user = userService.createUser(request, role);
        return ResponseEntity.ok(
                BaseResponse.success(role + " created successfully with ID: " + user.getId())
        );
    }
}