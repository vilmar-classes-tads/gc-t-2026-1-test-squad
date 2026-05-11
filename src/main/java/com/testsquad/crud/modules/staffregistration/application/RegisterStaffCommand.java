package com.testsquad.crud.modules.staffregistration.application;

import com.testsquad.crud.modules.staffregistration.domain.model.AcademicDegree;
import com.testsquad.crud.modules.staffregistration.domain.model.Gender;
import com.testsquad.crud.modules.staffregistration.domain.model.StaffType;

public record RegisterStaffCommand(
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
