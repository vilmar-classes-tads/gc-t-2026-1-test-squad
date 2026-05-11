package com.testsquad.crud.modules.staffregistration.application;

import com.testsquad.crud.modules.staffregistration.application.exception.CpfAlreadyRegisteredException;
import com.testsquad.crud.modules.staffregistration.application.exception.EmailAlreadyRegisteredException;
import com.testsquad.crud.modules.staffregistration.domain.model.AcademicDegree;
import com.testsquad.crud.modules.staffregistration.domain.model.Role;
import com.testsquad.crud.modules.staffregistration.domain.model.StaffType;
import com.testsquad.crud.modules.staffregistration.domain.model.User;
import com.testsquad.crud.modules.staffregistration.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterStaffServiceTest {

    private RegisterStaffService service;

    @BeforeEach
    void setUp() {
        service = new RegisterStaffService(new UserRepositoryFake(), new BCryptPasswordEncoder());
    }

    @Test
    void shouldRegisterStaffWithNormalizedCpfAndLowercaseEmail() {
        var user = service.register(command("Maria da Silva", "123.456.789-01", "MARIA@IFPE.EDU.BR"));

        assertEquals("12345678901", user.getCpf());
        assertEquals("maria@ifpe.edu.br", user.getEmail());
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void shouldHashPasswordAndAssignDefaultRoles() {
        var user = service.register(command("Maria da Silva", "123.456.789-01", "MARIA@IFPE.EDU.BR"));

        org.junit.jupiter.api.Assertions.assertNotEquals("secret123", user.getPasswordHash());
        org.junit.jupiter.api.Assertions.assertTrue(user.getPasswordHash().startsWith("$2"));
        org.junit.jupiter.api.Assertions.assertTrue(user.getRoles().contains(Role.ROLE_COORDINATOR));
        org.junit.jupiter.api.Assertions.assertTrue(user.getRoles().contains(Role.ROLE_REVIEWER));
    }

    @Test
    void shouldNotAllowDuplicateCpf() {
        service.register(command("Maria da Silva", "12345678901", "maria@ifpe.edu.br"));

        assertThrows(CpfAlreadyRegisteredException.class,
                () -> service.register(command("Joao Souza", "12345678901", "joao@ifpe.edu.br")));
    }

    @Test
    void shouldNotAllowDuplicateEmail() {
        service.register(command("Maria da Silva", "12345678901", "maria@ifpe.edu.br"));

        assertThrows(EmailAlreadyRegisteredException.class,
                () -> service.register(command("Joao Souza", "99988877766", "maria@ifpe.edu.br")));
    }

    @Test
    void shouldRequirePasswordWithAtLeastSixCharacters() {
        assertThrows(IllegalArgumentException.class,
                () -> service.register(new RegisterStaffCommand(
                        "Maria da Silva",
                        null,
                        "12345678901",
                        "maria@ifpe.edu.br",
                        "12345",
                        StaffType.FACULTY,
                        "Downtown Campus",
                        "Computer Science",
                        AcademicDegree.MASTERS,
                        null,
                        null,
                        null
                )));
    }

    @Test
    void shouldRequireIfpeInstitutionalEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.register(command("Maria da Silva", "12345678901", "maria@gmail.com")));

        assertEquals("Email must use the institutional domain @ifpe.edu.br", exception.getMessage());
    }

    private RegisterStaffCommand command(String fullName, String cpf, String email) {
        return new RegisterStaffCommand(
                fullName,
                null,
                cpf,
                email,
                "secret123",
                StaffType.FACULTY,
                "Downtown Campus",
                "Computer Science",
                AcademicDegree.MASTERS,
                null,
                "https://lattes.cnpq.br/1234567890123456",
                "+55 83 99999-9999"
        );
    }

    private static class UserRepositoryFake implements UserRepository {

        private final List<User> users = new ArrayList<>();

        @Override
        public boolean existsByCpf(String cpf) {
            return users.stream().anyMatch(user -> user.getCpf().equals(cpf));
        }

        @Override
        public boolean existsByEmail(String email) {
            return users.stream().anyMatch(user -> user.getEmail().equals(email));
        }

        @Override
        public User save(User user) {
            users.add(user);
            return user;
        }

        @Override
        public List<User> findAll() {
            return users;
        }

        @Override
        public Optional<User> findByCpf(String cpf) {
            return users.stream().filter(user -> user.getCpf().equals(cpf)).findFirst();
        }

        @Override
        public Optional<User> findByEmail(String email) {
            return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
        }
    }
}
