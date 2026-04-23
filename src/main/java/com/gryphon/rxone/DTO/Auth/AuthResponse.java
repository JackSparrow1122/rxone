package com.gryphon.rxone.DTO.Auth;

import com.gryphon.rxone.model.Organisation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";

    private Long expiresIn;

    private UserData user;

    private Organisation organisation;

    @Data
    @Builder
    public static class UserData {
        private UUID id;
        private String name;
        private String email;
        private String role;
        private String phoneNumber;
        private OrganisationData organisationData;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OrganisationData{
            private UUID id;
            private String name;
            private String logoUrl;
        }
    }
}