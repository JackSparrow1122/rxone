package com.gryphon.rxone.DTO.User;

import com.gryphon.rxone.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestPatch {

    private String name;

    private String phoneNumber;

    private Role role;

    private Map<String, Object> extraFields;
}
