package ru.mkonovalov.jurdoc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mkonovalov.jurdoc.core.router.Router;
import ru.mkonovalov.jurdoc.payload.ApiResponse;
import ru.mkonovalov.jurdoc.payload.dto.DocumentDto;
import ru.mkonovalov.jurdoc.payload.request.DocumentFilter;
import ru.mkonovalov.jurdoc.services.DocumentService;

import java.security.Principal;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService service;

    @GetMapping(Router.Management.Document.Id.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DocumentDto> getById(@PathVariable Long id) {
        return ApiResponse.success(service.getById(id));
    }

    @GetMapping(Router.Management.Document.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<DocumentDto>> getAll(DocumentFilter filter,
                                                 @RequestParam int page,
                                                 @RequestParam int size) {
        return ApiResponse.success(service.getAll(filter, page, size));
    }

    @PostMapping(Router.Management.Document.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DocumentDto> create(@RequestBody DocumentDto dto, Principal principal) {
        return ApiResponse.success(service.create(dto, principal));
    }

    @PutMapping(Router.Management.Document.Id.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DocumentDto> update(@PathVariable Long id, @RequestBody DocumentDto dto, Principal principal) {
        return ApiResponse.success(service.update(id, dto, principal));
    }

    @DeleteMapping(Router.Management.Document.Id.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success();
    }

    @GetMapping(Router.Document.Id.ROOT)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<DocumentDto> getByIdClient(@PathVariable Long id) {
        return ApiResponse.success(service.getById(id));
    }

    @GetMapping(Router.Document.ROOT)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<Page<DocumentDto>> getAllClient(DocumentFilter filter,
                                                 @RequestParam int page,
                                                 @RequestParam int size) {
        return ApiResponse.success(service.getAll(filter, page, size));
    }

    @PostMapping(Router.Document.ROOT)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<DocumentDto> createClient(@RequestBody DocumentDto dto, Principal principal) {
        return ApiResponse.success(service.create(dto, principal));
    }

    @PutMapping(Router.Document.Id.ROOT)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<DocumentDto> updateClient(@PathVariable Long id, @RequestBody DocumentDto dto, Principal principal) {
        return ApiResponse.success(service.update(id, dto, principal));
    }
}
