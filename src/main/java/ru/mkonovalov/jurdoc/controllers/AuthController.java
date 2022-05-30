package ru.mkonovalov.jurdoc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mkonovalov.jurdoc.core.router.Router;
import ru.mkonovalov.jurdoc.payload.ApiResponse;
import ru.mkonovalov.jurdoc.payload.request.LoginRequest;
import ru.mkonovalov.jurdoc.payload.response.AuthenticationResponse;
import ru.mkonovalov.jurdoc.services.AuthenticationService;

import javax.validation.Valid;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping(Router.Authentication.ROOT)
    private ApiResponse<AuthenticationResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(service.authenticate(request));
    }
}
