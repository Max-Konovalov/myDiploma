package ru.mkonovalov.jurdoc.payload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DocumentDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private Long idCreatedBy;
    private String extension;
    private String attachmentId;
    private Long documentPackageId;
    private Long documentParentId;

    private Set<DocumentDto> documentDtos;

    public DocumentDto(Long id, String name, LocalDateTime createdAt, Long idCreatedBy, String extension, String attachmentId, Long documentPackageId, Set<DocumentDto> documentDtos) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.idCreatedBy = idCreatedBy;
        this.extension = extension;
        this.attachmentId = attachmentId;
        this.documentPackageId = documentPackageId;
        this.documentDtos = documentDtos;
    }
}
