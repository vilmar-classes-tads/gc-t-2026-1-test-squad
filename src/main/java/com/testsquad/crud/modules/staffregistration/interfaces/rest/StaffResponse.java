package com.testsquad.crud.modules.staffregistration.interfaces.rest;

import com.testsquad.crud.modules.staffregistration.domain.model.User;

import java.util.Set;
import java.util.UUID;

public record StaffResponse(
        UUID id,
        String fullName,
        String socialName,
        String cpf,
        String email,
        String gender,
        String lattesLink,
        String phone,
        String staffType,
        String campus,
        String academicDegree,
        String educationArea,
        Set<String> roles
) {
    public static StaffResponse fromDomain(User user) {
        return new StaffResponse(
                user.getId(),
                user.getFullName(),
                user.getSocialName(),
                user.getCpf(),
                user.getEmail(),
                user.getGender() == null ? null : user.getGender().name(),
                user.getLattesLink(),
                user.getPhone(),
                user.getProfile().getStaffType().name(),
                user.getProfile().getCampus(),
                user.getProfile().getAcademicDegree().name(),
                user.getProfile().getEducationArea(),
                user.getRoles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet())
        );
    }
}
