package ru.mkonovalov.jurdoc.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mkonovalov.jurdoc.core.exceptions.NotFoundException;
import ru.mkonovalov.jurdoc.domain.entity.Document;
import ru.mkonovalov.jurdoc.domain.entity.DocumentPackage;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.domain.repository.DocumentPackageRepository;
import ru.mkonovalov.jurdoc.domain.repository.DocumentRepository;
import ru.mkonovalov.jurdoc.domain.repository.UserRepository;
import ru.mkonovalov.jurdoc.payload.dto.DocumentDto;
import ru.mkonovalov.jurdoc.services.storage.AttachmentService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentMapper {
    private final DocumentRepository repository;
    private final AttachmentService attachmentService;
    private final UserRepository userRepository;
    private final DocumentPackageRepository documentPackageRepository;

    public DocumentDto toDto(Document entity) {

        final DocumentDto result = new DocumentDto(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getCreatedBy().getId(),
                entity.getExtension(),
                entity.getAttachment().getId(),
                entity.getDocumentPackage().getId(),
                entity.getReferrals().stream().map(this::toDto).collect(Collectors.toSet()));

        if (entity.getParentDocument() != null && entity.getParentDocument().getId() != null) {
            result.setDocumentParentId(entity.getParentDocument().getId());
        }
        return result;
    }

    public List<DocumentDto> toDto(List<Document> entities) {
        if (entities == null || entities.isEmpty()) return new ArrayList<>();

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Document toEntity(DocumentDto dto, Principal principal) {
        if (dto == null) return null;

        Document document = new Document();
        document.setName(dto.getName());
        document.setCreatedAt(LocalDateTime.now());
        document.setAttachment(attachmentService.get(dto.getAttachmentId()));
        document.setExtension(document.getAttachment().getExtension());

        if (dto.getDocumentParentId() != null) {
            Document parent = repository.findById(dto.getDocumentParentId())
                    .orElseThrow(() -> new NotFoundException(Document.class, dto.getDocumentParentId()));

            document.setParentDocument(
                    parent
            );

            document.setDocumentPackage(
                    parent.getDocumentPackage()
            );
        } else {
            document.setDocumentPackage(
                    documentPackageRepository.findById(dto.getDocumentPackageId())
                            .orElseThrow(() -> new NotFoundException(DocumentPackage.class, dto.getDocumentPackageId()))
            );
        }

        document.setCreatedBy(
                userRepository.findByUsername(principal.getName())
                        .orElseThrow(() -> new NotFoundException(User.class, principal.getName()))
        );


        return document;
    }

    public List<Document> toEntity(List<DocumentDto> dtos, Principal principal) {
        if (dtos == null || dtos.isEmpty()) return new ArrayList<>();

        return dtos.stream()
                .map(item -> toEntity(item, principal))
                .collect(Collectors.toList());
    }
}
