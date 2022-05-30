package ru.mkonovalov.jurdoc.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.mkonovalov.jurdoc.core.exceptions.NotFoundException;
import ru.mkonovalov.jurdoc.domain.entity.DocumentPackage;
import ru.mkonovalov.jurdoc.domain.mappers.DocumentPackageMapper;
import ru.mkonovalov.jurdoc.domain.repository.DocumentPackageRepository;
import ru.mkonovalov.jurdoc.payload.dto.DocumentPackageDto;
import ru.mkonovalov.jurdoc.services.DocumentPackageService;
import ru.mkonovalov.jurdoc.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentPackageServiceImpl implements DocumentPackageService {
    private final DocumentPackageRepository repository;
    private final UserService userService;
    private final DocumentPackageMapper mapper;

    @Override
    public DocumentPackageDto getById(Long id) {
        return mapper.toDto(
                repository.findById(id).orElseThrow(() -> new NotFoundException(DocumentPackage.class, id))
        );
    }

    @Override
    public Page<DocumentPackageDto> getAll(int page, int size) {
        Page<DocumentPackage> entityPage = repository.findAll(PageRequest.of(page, size));

        return new PageImpl<>(
                mapper.toDto(entityPage.toList()),
                entityPage.getPageable(),
                entityPage.getTotalElements()
        );
    }

    @Override
    public DocumentPackageDto create(DocumentPackageDto dto) {
        return mapper.toDto(
                repository.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Override
    public DocumentPackageDto update(Long id, DocumentPackageDto dto) {
        DocumentPackage updating = repository.findById(id).orElseThrow(() -> new NotFoundException(DocumentPackage.class, id));

        DocumentPackage entity = mapper.toEntity(dto);

        entity.setId(updating.getId());
        return mapper.toDto(
                repository.save(
                        entity
                )
        );
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }

        throw new NotFoundException(DocumentPackage.class, id);
    }

    @Override
    public List<DocumentPackageDto> getByPrincipal(Principal principal) {
        return mapper.toDto(
                new ArrayList<>(userService.getByPrincipal(principal).getAccessedDocumentPackages())
        );
    }
}
