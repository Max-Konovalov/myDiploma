package ru.mkonovalov.jurdoc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mkonovalov.jurdoc.core.router.Router;
import ru.mkonovalov.jurdoc.services.storage.AttachmentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService service;

    @GetMapping(Router.Attachment.Id.ROOT)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String id) {

        Resource file = service.load(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(Router.Attachment.ROOT)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        return service.save(file);
    }
}

