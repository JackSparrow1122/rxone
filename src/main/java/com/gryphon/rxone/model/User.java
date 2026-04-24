package com.gryphon.rxone.model;

import com.gryphon.rxone.enums.PasswordProvider;
import com.gryphon.rxone.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name="users",indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_phone_number", columnList = "phone_number"),
        @Index(name = "idx_user_name", columnList = "name")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @ToString.Exclude
    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "password_provider")
    @Builder.Default
    private PasswordProvider passwordProvider = PasswordProvider.LOCAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation;
}