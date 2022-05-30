package ru.mkonovalov.jurdoc.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.payload.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserDto toDto(User entity) {
        return new UserDto(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getFirstName(), entity.getLastName(), entity.getMiddleName(), roleMapper.toERole(entity.getRole()));
    }
    public List<UserDto> toDto(List<User> entities) {
        if (entities == null || entities.isEmpty()) return new ArrayList<>();

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public User toEntity(UserDto dto) {
        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMiddleName(dto.getMiddleName());
        user.setRole(roleMapper.toEntity(dto.getRole()));

        return user;
    }

    public void update(UserDto dto, User entity) {
        if (dto.getUsername() != null) {
            entity.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null) {
            entity.setPassword(dto.getPassword());
        }

        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }

        if (dto.getMiddleName() != null) {
            entity.setMiddleName(dto.getMiddleName());
        }

        if (dto.getRole() != null) {
            entity.setRole(roleMapper.toEntity(dto.getRole()));
        }
    }
}
