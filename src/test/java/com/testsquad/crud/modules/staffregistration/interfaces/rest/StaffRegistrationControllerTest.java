package com.testsquad.crud.modules.staffregistration.interfaces.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StaffRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterStaffThroughApi() throws Exception {
        mockMvc.perform(post("/api/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Ana Pereira",
                                  "socialName": "Ana",
                                  "cpf": "321.654.987-00",
                                  "email": "ana.pereira@ifpe.edu.br",
                                  "password": "secret123",
                                  "staffType": "FACULTY",
                                  "campus": "North Zone Campus",
                                  "educationArea": "Education",
                                  "academicDegree": "DOCTORATE",
                                  "gender": "OTHER",
                                  "lattesLink": "https://lattes.cnpq.br/1234567890123456",
                                  "phone": "+55 83 99999-9999"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Registration completed successfully. You can now sign in."))
                .andExpect(jsonPath("$.redirectTo").value("/login"))
                .andExpect(jsonPath("$.staff.cpf").value("32165498700"))
                .andExpect(jsonPath("$.staff.email").value("ana.pereira@ifpe.edu.br"))
                .andExpect(jsonPath("$.staff.campus").value("North Zone Campus"))
                .andExpect(jsonPath("$.staff.staffType").value("FACULTY"));
    }

    @Test
    void shouldRequireAuthenticationToListStaff() throws Exception {
        mockMvc.perform(get("/api/staff"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAuthenticatedRegisteredUserToListStaff() throws Exception {
        mockMvc.perform(post("/api/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName": "Bruno Lima",
                                  "socialName": "Bruno",
                                  "cpf": "741.852.963-00",
                                  "email": "bruno.lima@ifpe.edu.br",
                                  "password": "secret456",
                                  "staffType": "FACULTY",
                                  "campus": "North Zone Campus",
                                  "educationArea": "Education",
                                  "academicDegree": "DOCTORATE",
                                  "gender": "OTHER",
                                  "lattesLink": "https://lattes.cnpq.br/1234567890123456",
                                  "phone": "+55 83 98888-7777"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/staff")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("bruno.lima@ifpe.edu.br", "secret456")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.email=='bruno.lima@ifpe.edu.br')]").exists());
    }
}
