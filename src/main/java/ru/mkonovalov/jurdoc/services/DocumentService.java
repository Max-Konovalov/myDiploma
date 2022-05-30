package ru.mkonovalov.jurdoc.services;

import org.springframework.data.domain.Page;
import ru.mkonovalov.jurdoc.payload.dto.DocumentDto;
import ru.mkonovalov.jurdoc.payload.request.DocumentFilter;

import java.security.Principal;

public interface DocumentService {
    DocumentDto getById(Long id);
    Page<DocumentDto> getAll(DocumentFilter filter, int page, int size);
    DocumentDto create(DocumentDto dto, Principal principal);
    DocumentDto update(Long id, DocumentDto dto, Principal principal);

    DocumentDto getByIdClient(Long id, Principal principal);
    Page<DocumentDto> getAllClient(DocumentFilter filter, int page, int size, Principal principal);
    DocumentDto createClient(DocumentDto dto, Principal principal);
    DocumentDto updateClient(Long id, DocumentDto dto, Principal principal);

    void delete(Long id);
}
