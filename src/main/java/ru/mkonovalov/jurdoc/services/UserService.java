package ru.mkonovalov.jurdoc.services;

import org.springframework.data.domain.Page;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.payload.dto.UserDto;
import ru.mkonovalov.jurdoc.payload.request.UserFilter;

import java.security.Principal;

public interface UserService {
    UserDto getById(Long id);
    Page<UserDto> getAll(UserFilter filter, int page, int size);
    UserDto create(UserDto dto);
    UserDto update(Long id, UserDto dto);
    void delete(Long id);

    User getByPrincipal(Principal principal);
}
