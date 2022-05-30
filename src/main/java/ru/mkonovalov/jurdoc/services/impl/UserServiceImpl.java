package ru.mkonovalov.jurdoc.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mkonovalov.jurdoc.core.exceptions.NotFoundException;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.domain.mappers.UserMapper;
import ru.mkonovalov.jurdoc.domain.repository.UserRepository;
import ru.mkonovalov.jurdoc.domain.spec.UserSpec;
import ru.mkonovalov.jurdoc.payload.dto.UserDto;
import ru.mkonovalov.jurdoc.payload.request.UserFilter;
import ru.mkonovalov.jurdoc.services.UserService;
import ru.mkonovalov.jurdoc.utils.FilterProcessor;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    //Filter => Spec
    private final FilterProcessor<UserFilter, User> filterProcessor = FilterProcessor
            .strict(UserFilter::getUsernameLike, UserSpec::usernameLike)
            .strict(UserFilter::getEmailLike, UserSpec::emailLike)
            .strict(UserFilter::getFirstNameLike, UserSpec::firstNameLike)
            .strict(UserFilter::getLastNameLike, UserSpec::lastNameLike)
            .strict(UserFilter::getMiddleNameLike, UserSpec::middleNameLike)
            .strict(UserFilter::getRoleIs, UserSpec::roleIs)
            .build();

    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(
                repository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(User.class, id))
        );
    }

    @Override
    public Page<UserDto> getAll(UserFilter filter, int page, int size) {
        Page<User> entityPage = repository.findAll(filterProcessor.buildSpec(filter), PageRequest.of(page, size));

        return new PageImpl<>(
                mapper.toDto(entityPage.toList()),
                entityPage.getPageable(),
                entityPage.getTotalElements()
        );
    }

    @Override
    public UserDto create(UserDto dto) {
        User entity = mapper.toEntity(dto);
        entity.setPassword(encoder.encode(dto.getPassword()));
        entity.setId(null);
        
        return mapper.toDto(
                repository.save(
                        entity
                )
        );
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));

        mapper.update(dto, entity);

        if (dto.getPassword() != null) {
            entity.setPassword(encoder.encode(dto.getPassword()));
        }

        return mapper.toDto(
                repository.save(entity)
        );
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }

        throw new NotFoundException(User.class, id);
    }

    @Override
    public User getByPrincipal(Principal principal) {
        return repository.findByUsername(principal.getName()).orElseThrow(() -> new NotFoundException(User.class, principal.getName()));
    }
}
