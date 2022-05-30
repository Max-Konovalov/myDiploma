package ru.mkonovalov.jurdoc.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mkonovalov.jurdoc.core.exceptions.NotFoundException;
import ru.mkonovalov.jurdoc.domain.entity.DocumentPackage;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.domain.repository.UserRepository;
import ru.mkonovalov.jurdoc.payload.dto.DocumentPackageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DocumentPackageMapper {
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    public DocumentPackageDto toDto(DocumentPackage entity) {
        return new DocumentPackageDto(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getClient().getId(),
                entity.getDocuments().stream()
                        .filter(item -> item.getParentDocument() == null)
                        .map(documentMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }

    public List<DocumentPackageDto> toDto(List<DocumentPackage> entities) {
        if (entities == null || entities.isEmpty()) return new ArrayList<>();

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DocumentPackage toEntity(DocumentPackageDto dto) {
        if (dto.getClient() == null) throw new NotFoundException(User.class);

        User client = userRepository.findById(dto.getClient()).orElseThrow(() -> new NotFoundException(User.class, dto.getClient()));

        DocumentPackage entity = new DocumentPackage();

        entity.setName(dto.getName());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setClient(client);

        return entity;
    }
}