package com.testsquad.crud.modules.staffregistration.domain.model;

import java.util.Objects;

public class StaffProfile {

    private final StaffType staffType;
    private final String campus;
    private final AcademicDegree academicDegree;
    private final String educationArea;

    public StaffProfile(StaffType staffType, String campus, AcademicDegree academicDegree, String educationArea) {
        this.staffType = Objects.requireNonNull(staffType, "Staff type is required");
        this.campus = validateText(campus, "Campus is required");
        this.academicDegree = Objects.requireNonNull(academicDegree, "Academic degree is required");
        this.educationArea = validateText(educationArea, "Education area is required");
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public String getCampus() {
        return campus;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    public String getEducationArea() {
        return educationArea;
    }

    private String validateText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
