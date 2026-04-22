package com.gryphon.rxone.DTO;

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

    @Data
    @Builder
    public static class UserData {
        private Long id;
        private String name;
        private String email;
        private String role;
    }
}