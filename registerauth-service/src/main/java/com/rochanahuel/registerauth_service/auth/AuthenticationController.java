package com.rochanahuel.registerauth_service.auth;

import com.rochanahuel.registerauth_service.auth.authService.AuthenticationService;
import com.rochanahuel.registerauth_service.dto.AuthenticationRequest;
import com.rochanahuel.registerauth_service.dto.AuthenticationResponse;
import com.rochanahuel.registerauth_service.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
