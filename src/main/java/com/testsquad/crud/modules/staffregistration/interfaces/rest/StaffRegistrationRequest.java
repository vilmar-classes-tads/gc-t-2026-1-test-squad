package com.testsquad.crud.modules.staffregistration.interfaces.rest;

import com.testsquad.crud.modules.staffregistration.domain.model.AcademicDegree;
import com.testsquad.crud.modules.staffregistration.domain.model.Gender;
import com.testsquad.crud.modules.staffregistration.domain.model.StaffType;

public record StaffRegistrationRequest(
        String fullName,
        String socialName,
        String cpf,
        String email,
        String password,
        StaffType staffType,
        String campus,
        String educationArea,
        AcademicDegree academicDegree,
        Gender gender,
        String lattesLink,
        String phone
) {
}
