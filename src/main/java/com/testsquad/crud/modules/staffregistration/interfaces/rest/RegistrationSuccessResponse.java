package com.testsquad.crud.modules.staffregistration.interfaces.rest;

public record RegistrationSuccessResponse(
        String message,
        String redirectTo,
        StaffResponse staff
) {
}
