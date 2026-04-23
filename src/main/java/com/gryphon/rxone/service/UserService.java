package com.gryphon.rxone.service;
import java.util.List;
import java.util.UUID;

import com.gryphon.rxone.DTO.User.CreateUserRequest;
import com.gryphon.rxone.DTO.User.UserResponse;
import com.gryphon.rxone.enums.PasswordProvider;
import com.gryphon.rxone.enums.Role;
import com.gryphon.rxone.model.Organisation;
import com.gryphon.rxone.repository.OrganisationRepository;
import com.gryphon.rxone.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.gryphon.rxone.DTO.User.UpdateUserRequest;
import com.gryphon.rxone.DTO.User.UpdateUserRequestPatch;
import com.gryphon.rxone.model.User;
import com.gryphon.rxone.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final OrganisationRepository organisationRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User currentUser = userDetails.getUser();

        if (currentUser.getRole() == Role.SUPERADMIN) {
            return repository.findAll();
        }
        return repository.findByOrganisationId(currentUser.getOrganisation().getId());
    }

    public User getUserById (UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found or ID is not provided"));
    }

    public User createUser(CreateUserRequest request, Role role){

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User currentUser = userDetails.getUser();

        validateRoleCreation(currentUser.getRole(), role);

        if (currentUser.getRole() != Role.SUPERADMIN &&
                !currentUser.getOrganisation().getId().equals(request.getOrganisationId())) {

            throw new ResponseStatusException(FORBIDDEN, "Cannot create user for a different organisation");
        }

        Organisation organisation = organisationRepository.findById(request.getOrganisationId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Invalid organisation ID"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(role)
                .phoneNumber(request.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .passwordProvider(PasswordProvider.LOCAL)
                .organisation(organisation)
                .build();

        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Email or phone already exists");
        }
    }

    public User patchUser(UUID id, UpdateUserRequestPatch request) {

        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        return repository.save(user);
    }

    public void deleteUser(UUID id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));

        repository.delete(user);
    }

    public UserResponse toUserResponse(User user) {


        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .organisation(user.getOrganisation() != null
                        ? UserResponse.OrganisationData.builder()
                          .id(user.getOrganisation().getId())
                          .name(user.getOrganisation().getName())
                          .build()
                        : null)
                .build();
    }

    private void validateRoleCreation(Role creatorRole, Role targetRole) {
        if (creatorRole != Role.SUPERADMIN && targetRole == Role.SUPERADMIN) {
            throw new ResponseStatusException(FORBIDDEN, "Cannot create SUPERADMIN");
        }
    }

    // PUT method retained as commented reference only.

    //    public User updateUser(UUID id, UpdateUserRequest request) {
//
//        User user = repository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
//
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        if (request.getPassword() != null) {
//            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
//        }
//        user.setPhoneNumber(request.getPhoneNumber());
//
//        return repository.save(user);
//    }

}
