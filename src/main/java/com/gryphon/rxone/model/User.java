package com.gryphon.rxone.model;

import com.gryphon.rxone.enums.PasswordProvider;
import com.gryphon.rxone.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name="users")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_fields",columnDefinition = "jsonb")
    private Map<String, Object> extraFields;
}