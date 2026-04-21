package com.gryphon.rxone.DTO;



import com.gryphon.rxone.model.PasswordProvider;
import com.gryphon.rxone.model.Role;

import lombok.Data;

@Data
public class RegisterDto {

    private String name;
    private long phoneNumber;
    private String email;
    private String passwordHash;

    private Role role;
    private PasswordProvider passwordProvider;

}
