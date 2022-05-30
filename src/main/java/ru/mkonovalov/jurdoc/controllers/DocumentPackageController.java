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
import ru.mkonovalov.jurdoc.payload.dto.DocumentPackageDto;
import ru.mkonovalov.jurdoc.services.DocumentPackageService;

import java.security.Principal;
import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class DocumentPackageController {
    private final DocumentPackageService service;

    @GetMapping(Router.Document.My.ROOT)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<List<DocumentPackageDto>> getDocumentPackagesByPrincipal(Principal principal) {
        return ApiResponse.success(service.getByPrincipal(principal));
    }

    @GetMapping(Router.Management.Document.Package.Id.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DocumentPackageDto> getById(@PathVariable Long id) {
        return ApiResponse.success(service.getById(id));
    }

    @GetMapping(Router.Management.Document.Package.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<DocumentPackageDto>> getAll(@RequestParam int page,
                                                 @RequestParam int size) {
        return ApiResponse.success(service.getAll(page, size));
    }

    @PostMapping(Router.Management.Document.Package.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DocumentPackageDto> create(@RequestBody DocumentPackageDto dto) {
        return ApiResponse.success(service.create(dto));
    }

    @PutMapping(Router.Management.Document.Package.Id.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DocumentPackageDto> update(@PathVariable Long id, @RequestBody DocumentPackageDto dto) {
        return ApiResponse.success(service.update(id, dto));
    }

    @DeleteMapping(Router.Management.Document.Package.Id.ROOT)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success();
    }
}
