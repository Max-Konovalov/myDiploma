package ru.mkonovalov.jurdoc.services;

import ru.mkonovalov.jurdoc.payload.request.LoginRequest;
import ru.mkonovalov.jurdoc.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(LoginRequest request);
}
