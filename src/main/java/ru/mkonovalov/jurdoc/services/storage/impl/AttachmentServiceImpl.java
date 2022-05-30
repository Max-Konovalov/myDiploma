package ru.mkonovalov.jurdoc.services.storage.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mkonovalov.jurdoc.core.exceptions.NotFoundException;
import ru.mkonovalov.jurdoc.domain.entity.Attachment;
import ru.mkonovalov.jurdoc.domain.repository.AttachmentRepository;
import ru.mkonovalov.jurdoc.services.storage.AttachmentService;
import ru.mkonovalov.jurdoc.services.storage.StorageService;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository repository;
    private final StorageService storage;

    @Override
    public String save(MultipartFile file) {
        Attachment attachment = new Attachment(FilenameUtils.getExtension(file.getOriginalFilename()));
        storage.store(file, attachment.getFilename());

        return repository.save(attachment).getId();
    }

    @Override
    public Attachment get(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Attachment.class, id));
    }

    @Override
    public Set<Attachment> get(Set<String> ids) {
        return new HashSet<>(repository.findAllById(ids));
    }

    @Override
    public Resource load(String id) {
        Attachment attachment = get(id);

        return storage.loadAsResource(attachment.getFilename());
    }

    @Transactional
    @Override
    public void deleteAttachment(Attachment attachment) {
        repository.delete(attachment);
        storage.deleteByResource(attachment.getFilename());
    }
}
