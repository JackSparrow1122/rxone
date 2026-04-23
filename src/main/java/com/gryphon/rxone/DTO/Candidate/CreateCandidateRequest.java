package com.gryphon.rxone.DTO.Candidate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.gryphon.rxone.DTO.User.CreateUserRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String phoneNumber;

    private Map<String, Object> extraFields;

    @NotNull(message = "Organisation ID is required")
    @JsonAlias("organisation_id")
    private UUID organisationId;

    public CreateUserRequest toCreateUserRequest() {
        CreateUserRequest r = new CreateUserRequest();
        r.setName(this.name);
        r.setEmail(this.email);
        r.setPassword(this.password);
        r.setPhoneNumber(this.phoneNumber);
        r.setOrganisationId(this.organisationId);
        return r;
    }
}
