package com.testsquad.crud.modules.staffregistration.interfaces.rest;

import com.testsquad.crud.modules.staffregistration.application.RegisterStaffCommand;
import com.testsquad.crud.modules.staffregistration.application.RegisterStaffService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffRegistrationController {

    private final RegisterStaffService registerStaffService;

    public StaffRegistrationController(RegisterStaffService registerStaffService) {
        this.registerStaffService = registerStaffService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationSuccessResponse register(@RequestBody StaffRegistrationRequest request) {
        return new RegistrationSuccessResponse(
                "Registration completed successfully. You can now sign in.",
                "/login",
                StaffResponse.fromDomain(
                        registerStaffService.register(
                                new RegisterStaffCommand(
                                        request.fullName(),
                                        request.socialName(),
                                        request.cpf(),
                                        request.email(),
                                        request.password(),
                                        request.staffType(),
                                        request.campus(),
                                        request.educationArea(),
                                        request.academicDegree(),
                                        request.gender(),
                                        request.lattesLink(),
                                        request.phone()
                                )
                        )
                )
        );
    }

    @GetMapping
    public List<StaffResponse> listAll() {
        return registerStaffService.listAll()
                .stream()
                .map(StaffResponse::fromDomain)
                .toList();
    }
}
