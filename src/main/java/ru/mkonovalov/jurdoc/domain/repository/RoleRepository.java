package ru.mkonovalov.jurdoc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkonovalov.jurdoc.domain.entity.ERole;
import ru.mkonovalov.jurdoc.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    boolean existsByName(ERole name);
}

