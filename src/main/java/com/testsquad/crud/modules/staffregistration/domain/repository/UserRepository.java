package com.testsquad.crud.modules.staffregistration.domain.repository;

import com.testsquad.crud.modules.staffregistration.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    User save(User user);

    List<User> findAll();

    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(String email);
}
