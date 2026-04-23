package com.gryphon.rxone.service;
import java.util.List;
import java.util.UUID;

import com.gryphon.rxone.DTO.User.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.User.UpdateUserRequest;
import com.gryphon.rxone.DTO.User.UpdateUserRequestPatch;
import com.gryphon.rxone.model.User;
import com.gryphon.rxone.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById (UUID id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found or ID is not provided"));
        return user;
    }

    public String extraFields(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found or Email is not provided"));

        var extra = user.getExtraFields();
        if (extra == null) return "No extra fields available";

        String org = (String) extra.get("Org");
        String college = (String) extra.get("College");
        return "org= " + org + "  college= " + college;
    }

    public User updateUser(UUID id, UpdateUserRequest request) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getExtraFields() != null) {
            user.setExtraFields(request.getExtraFields());
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());

        return repository.save(user);
    }

    public User patchUser(UUID id, UpdateUserRequestPatch request) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getExtraFields() != null) {
            user.setExtraFields(request.getExtraFields());
        }

        return repository.save(user);
    }

    public String deleteUser(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.delete(user);
        return "User deleted successfully";
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .extraFields(user.getExtraFields())
                .build();
    }
}