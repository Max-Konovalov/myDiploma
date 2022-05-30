package ru.mkonovalov.jurdoc.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mkonovalov.jurdoc.domain.entity.User;
import ru.mkonovalov.jurdoc.domain.repository.UserRepository;
import ru.mkonovalov.jurdoc.payload.request.LoginRequest;
import ru.mkonovalov.jurdoc.payload.response.AuthenticationResponse;
import ru.mkonovalov.jurdoc.services.AuthenticationService;
import ru.mkonovalov.jurdoc.services.JwtService;

@Service
@RequiredArgsConstructor
class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);

        //don't need to check on user exists, because authentication ll drop the exception
        User user = userRepository.findByUsername(request.getUsername()).get();

        return new AuthenticationResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getRole().getName(),
                jwt
        );
    }
}
