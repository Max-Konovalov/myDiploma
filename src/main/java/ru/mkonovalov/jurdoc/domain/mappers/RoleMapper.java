package ru.mkonovalov.jurdoc.domain.mappers;

import org.springframework.stereotype.Component;
import ru.mkonovalov.jurdoc.domain.entity.ERole;
import ru.mkonovalov.jurdoc.domain.entity.Role;

@Component
public class RoleMapper {
    public ERole toERole(Role role) {
        return role.getName();
    }

    public Role toEntity(ERole role) {
        return new Role(role.getId(), role);
    }
}
