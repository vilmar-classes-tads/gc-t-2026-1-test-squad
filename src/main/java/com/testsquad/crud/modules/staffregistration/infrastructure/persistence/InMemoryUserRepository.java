package com.testsquad.crud.modules.staffregistration.infrastructure.persistence;

import com.testsquad.crud.modules.staffregistration.domain.model.User;
import com.testsquad.crud.modules.staffregistration.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static final Map<String, User> USERS_BY_CPF = new ConcurrentHashMap<>();
    private static final Map<String, User> USERS_BY_EMAIL = new ConcurrentHashMap<>();

    @Override
    public boolean existsByCpf(String cpf) {
        return USERS_BY_CPF.containsKey(cpf);
    }

    @Override
    public boolean existsByEmail(String email) {
        return USERS_BY_EMAIL.containsKey(email);
    }

    @Override
    public synchronized User save(User user) {
        if (USERS_BY_CPF.containsKey(user.getCpf())) {
            return USERS_BY_CPF.get(user.getCpf());
        }

        if (USERS_BY_EMAIL.containsKey(user.getEmail())) {
            return USERS_BY_EMAIL.get(user.getEmail());
        }

        USERS_BY_CPF.put(user.getCpf(), user);
        USERS_BY_EMAIL.put(user.getEmail(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(USERS_BY_CPF.values());
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return Optional.ofNullable(USERS_BY_CPF.get(cpf));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(USERS_BY_EMAIL.get(email == null ? null : email.trim().toLowerCase()));
    }
}
