package ru.mkonovalov.jurdoc.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentPackageDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private Long client;
    private Set<DocumentDto> documents;
}
