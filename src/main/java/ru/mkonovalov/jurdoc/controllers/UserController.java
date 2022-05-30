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
import ru.mkonovalov.jurdoc.domain.entity.ERole;
import ru.mkonovalov.jurdoc.payload.ApiResponse;
import ru.mkonovalov.jurdoc.payload.dto.UserDto;
import ru.mkonovalov.jurdoc.payload.request.UserFilter;
import ru.mkonovalov.jurdoc.services.UserService;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping(Router.User.Id.ROOT)
    public ApiResponse<UserDto> getById(@PathVariable Long id) {
        return ApiResponse.success(service.getById(id));
    }

    @GetMapping(Router.User.Roles.ROOT)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<ERole[]> getUserRoles() {
        return ApiResponse.success(ERole.values());
    }
    @GetMapping(Router.User.ROOT)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<Page<UserDto>> getAll(UserFilter filter, @RequestParam int page, @RequestParam int size) {
        return ApiResponse.success(service.getAll(filter, page, size));
    }

    @PostMapping(Router.User.ROOT)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<UserDto> create(@RequestBody UserDto dto) {
        return ApiResponse.success(service.create(dto));
    }

    @PutMapping(Router.User.Id.ROOT)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return ApiResponse.success(service.update(id, dto));
    }

    @DeleteMapping(Router.User.Id.ROOT)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success();
    }
}
