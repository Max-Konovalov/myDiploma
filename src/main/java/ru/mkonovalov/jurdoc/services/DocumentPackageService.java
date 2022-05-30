package ru.mkonovalov.jurdoc.services;

import org.springframework.data.domain.Page;
import ru.mkonovalov.jurdoc.payload.dto.DocumentPackageDto;

import java.security.Principal;
import java.util.List;

public interface DocumentPackageService {
    DocumentPackageDto getById(Long id);
    Page<DocumentPackageDto> getAll(int page, int size);
    DocumentPackageDto create(DocumentPackageDto dto);
    DocumentPackageDto update(Long id, DocumentPackageDto dto);
    void delete(Long id);

    List<DocumentPackageDto> getByPrincipal(Principal principal);
}
