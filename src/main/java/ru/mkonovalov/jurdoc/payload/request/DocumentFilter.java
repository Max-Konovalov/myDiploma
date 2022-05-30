package ru.mkonovalov.jurdoc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFilter implements Serializable {
    private String nameLike;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private String extensionLike;
    private Long createdByEq;
    private Long documentPackageEq;
}
