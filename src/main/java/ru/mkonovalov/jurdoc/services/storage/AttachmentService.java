package ru.mkonovalov.jurdoc.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.mkonovalov.jurdoc.domain.entity.Attachment;

import java.util.Set;

public interface AttachmentService {
    String save(MultipartFile file);

    Attachment get(String id);

    Set<Attachment> get(Set<String> ids);

    Resource load(String id);

    void deleteAttachment(Attachment attachment);
}
