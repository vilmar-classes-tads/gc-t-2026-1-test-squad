package com.testsquad.crud.modules.staffregistration.domain.model;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class User {

    private static final String INSTITUTIONAL_EMAIL_DOMAIN = "@ifpe.edu.br";

    private final UUID id;
    private final String fullName;
    private final String socialName;
    private final String cpf;
    private final String email;
    private final String passwordHash;
    private final Gender gender;
    private final String lattesLink;
    private final String phone;
    private final StaffProfile profile;
    private final Set<Role> roles;

    public User(
            UUID id,
            String fullName,
            String socialName,
            String cpf,
            String email,
            String passwordHash,
            Gender gender,
            String lattesLink,
            String phone,
            StaffProfile profile,
            Set<Role> roles
    ) {
        this.id = Objects.requireNonNull(id, "Id is required");
        this.fullName = validateFullName(fullName);
        this.socialName = normalizeOptionalText(socialName);
        this.cpf = normalizeCpf(cpf);
        this.email = normalizeEmail(email);
        this.passwordHash = validatePasswordHash(passwordHash);
        this.gender = gender;
        this.lattesLink = normalizeOptionalUrl(lattesLink);
        this.phone = normalizeOptionalPhone(phone);
        this.profile = Objects.requireNonNull(profile, "Profile is required");
        this.roles = Set.copyOf(Objects.requireNonNull(roles, "Roles are required"));
        if (this.roles.isEmpty()) {
            throw new IllegalArgumentException("At least one role is required");
        }
    }

    public static User create(
            String fullName,
            String socialName,
            String cpf,
            String email,
            String passwordHash,
            Gender gender,
            String lattesLink,
            String phone,
            StaffProfile profile,
            Set<Role> roles
    ) {
        return new User(
                UUID.randomUUID(),
                fullName,
                socialName,
                cpf,
                email,
                passwordHash,
                gender,
                lattesLink,
                phone,
                profile,
                roles
        );
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSocialName() {
        return socialName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLattesLink() {
        return lattesLink;
    }

    public String getPhone() {
        return phone;
    }

    public StaffProfile getProfile() {
        return profile;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    private String validateFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required");
        }
        return fullName.trim();
    }

    private String normalizeCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF is required");
        }

        String normalizedCpf = cpf.replaceAll("\\D", "");
        if (normalizedCpf.length() != 11) {
            throw new IllegalArgumentException("CPF must contain 11 digits");
        }
        return normalizedCpf;
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (!normalizedEmail.contains("@") || normalizedEmail.startsWith("@") || normalizedEmail.endsWith("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!normalizedEmail.endsWith(INSTITUTIONAL_EMAIL_DOMAIN)) {
            throw new IllegalArgumentException("Email must use the institutional domain @ifpe.edu.br");
        }
        return normalizedEmail;
    }

    private String validatePasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash is required");
        }
        return passwordHash;
    }

    private String normalizeOptionalText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String normalizeOptionalUrl(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalizedValue = value.trim();
        if (!(normalizedValue.startsWith("http://") || normalizedValue.startsWith("https://"))) {
            throw new IllegalArgumentException("Lattes link must start with http:// or https://");
        }
        return normalizedValue;
    }

    private String normalizeOptionalPhone(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
