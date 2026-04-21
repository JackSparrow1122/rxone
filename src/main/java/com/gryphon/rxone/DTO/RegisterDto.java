package com.gryphon.rxone.DTO;



import java.util.Map;

import com.gryphon.rxone.enums.PasswordProvider;
import com.gryphon.rxone.enums.Role;

import lombok.Data;

@Data
public class RegisterDto {

    private String name;
    private long phoneNumber;
    private String email;
    private String passwordHash;
    private PasswordProvider passwordProvider;
    private Role role;

    private Map<String, Object> extraFields;

}
