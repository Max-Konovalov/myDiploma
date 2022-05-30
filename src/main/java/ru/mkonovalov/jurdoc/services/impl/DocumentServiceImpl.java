package ru.mkonovalov.jurdoc.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.mkonovalov.jurdoc.core.exceptions.NotFoundException;
import ru.mkonovalov.jurdoc.domain.entity.Document;
import ru.mkonovalov.jurdoc.domain.entity.DocumentPackage;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.domain.mappers.DocumentMapper;
import ru.mkonovalov.jurdoc.domain.repository.DocumentPackageRepository;
import ru.mkonovalov.jurdoc.domain.repository.DocumentRepository;
import ru.mkonovalov.jurdoc.domain.spec.DocumentSpec;
import ru.mkonovalov.jurdoc.payload.dto.DocumentDto;
import ru.mkonovalov.jurdoc.payload.request.DocumentFilter;
import ru.mkonovalov.jurdoc.services.DocumentService;
import ru.mkonovalov.jurdoc.services.UserService;
import ru.mkonovalov.jurdoc.utils.FilterProcessor;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository repository;
    private final DocumentMapper mapper;
    private final UserService userService;
    private final DocumentPackageRepository documentPackageRepository;

    private final FilterProcessor<DocumentFilter, Document> filterProcessor = FilterProcessor
            .strict(DocumentFilter::getNameLike, DocumentSpec::nameLike)
            .strict(DocumentFilter::getCreatedAtFrom, DocumentSpec::createdAtFrom)
            .strict(DocumentFilter::getCreatedAtTo, DocumentSpec::createdAtTo)
            .strict(DocumentFilter::getExtensionLike, DocumentSpec::extensionLike)
            .strict(DocumentFilter::getCreatedByEq, DocumentSpec::createdByEq)
            .strict(DocumentFilter::getDocumentPackageEq, DocumentSpec::documentPackageEq)
            .build();

    @Override
    public DocumentDto getById(Long id) {
        return mapper.toDto(
                repository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(Document.class, id))
        );
    }

    @Override
    public Page<DocumentDto> getAll(DocumentFilter filter, int page, int size) {
        Page<Document> entityPage = repository.findAll(filterProcessor.buildSpec(filter), PageRequest.of(page, size));

        return new PageImpl<>(
                mapper.toDto(entityPage.toList()),
                entityPage.getPageable(),
                entityPage.getTotalElements()
        );
    }

    @Override
    public DocumentDto create(DocumentDto dto, Principal principal) {
        return mapper.toDto(
                repository.save(
                        mapper.toEntity(dto, principal)
                )
        );
    }

    @Override
    public DocumentDto update(Long id, DocumentDto dto, Principal principal) {
        Document document = repository.findById(id).orElseThrow(() -> new NotFoundException(Document.class, id));

        Document result = mapper.toEntity(dto, principal);
        result.setId(document.getId());


        return mapper.toDto(
                repository.save(
                    result
                )
        );
    }

    @Override
    public DocumentDto getByIdClient(Long id, Principal principal) {
        Document document = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Document.class, id));

        User current = userService.getByPrincipal(principal);

        if (current.hasAccessToDocument(document)) {
            return mapper.toDto(document);
        }

        throw new NotFoundException(Document.class, id);
    }

    @Override
    public Page<DocumentDto> getAllClient(DocumentFilter filter, int page, int size, Principal principal) {
        Page<Document> entityPage = repository.findAll(filterProcessor.buildSpec(filter), PageRequest.of(page, size));
        User current = userService.getByPrincipal(principal);

        List<Document> documents = entityPage.toList();
        long totalElements = entityPage.getTotalElements();

        for (Document document : documents) {
            if (!current.hasAccessToDocument(document)) {
                documents.remove(document);
                totalElements = totalElements-1;
            }
        }

        return new PageImpl<>(
                mapper.toDto(documents),
                entityPage.getPageable(),
                totalElements
        );
    }

    @Override
    public DocumentDto createClient(DocumentDto dto, Principal principal) {
        User current = userService.getByPrincipal(principal);

        if (!current.hasAccessToDocumentPackage(
                documentPackageRepository
                        .findById(dto.getDocumentPackageId())
                        .orElseThrow(() -> new NotFoundException(DocumentPackage.class, dto.getDocumentPackageId()))
        )) {
            throw new NotFoundException(DocumentPackage.class, dto.getDocumentPackageId());
        }

        return create(dto, principal);
    }

    @Override
    public DocumentDto updateClient(Long id, DocumentDto dto, Principal principal) {
        User current = userService.getByPrincipal(principal);
        Document updating = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Document.class, id));


        if (!current.hasAccessToDocumentPackage(
                documentPackageRepository
                        .findById(dto.getDocumentPackageId())
                        .orElseThrow(() -> new NotFoundException(DocumentPackage.class, dto.getDocumentPackageId()))
        ) || !current.hasAccessToDocumentPackage(
                updating.getDocumentPackage()
        )) {
            throw new NotFoundException(DocumentPackage.class, dto.getDocumentPackageId());
        }

        return update(id, dto, principal);
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }

        throw new NotFoundException(Document.class, id);
    }
}
