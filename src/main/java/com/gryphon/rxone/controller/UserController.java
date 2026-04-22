package com.gryphon.rxone.controller;


import java.util.List;

import com.gryphon.rxone.DTO.BaseResponse;
import com.gryphon.rxone.DTO.UpdateRequest;
import com.gryphon.rxone.DTO.UpdateRequestPatch;
import com.gryphon.rxone.DTO.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gryphon.rxone.model.User;
import com.gryphon.rxone.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin (originPatterns = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponse>>> getUsers() {
        List<UserResponse> users = userService.getUsers().stream()
                .map(userService::toUserResponse)
                .toList();
        return ResponseEntity.ok(BaseResponse.success(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> patchUser(
            @PathVariable long id,
            @RequestBody @Valid UpdateRequestPatch request
    ) {
        User user = userService.patchUser(id, request);
        return ResponseEntity.ok(BaseResponse.success(userService.toUserResponse(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> putUser(
            @PathVariable long id,
            @RequestBody @Valid UpdateRequest request
    ){
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(BaseResponse.success(userService.toUserResponse(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteUser(@PathVariable long id){
        return ResponseEntity.ok(BaseResponse.success(userService.deleteUser(id)));
    }
}