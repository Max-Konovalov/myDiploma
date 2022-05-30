package ru.mkonovalov.jurdoc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkonovalov.jurdoc.domain.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {
}
