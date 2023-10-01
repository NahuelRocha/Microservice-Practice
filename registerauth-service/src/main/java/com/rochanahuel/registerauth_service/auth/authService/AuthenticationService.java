package com.rochanahuel.registerauth_service.auth.authService;

import com.rochanahuel.registerauth_service.config.jwtService.JwtService;
import com.rochanahuel.registerauth_service.dto.AuthenticationRequest;
import com.rochanahuel.registerauth_service.dto.AuthenticationResponse;
import com.rochanahuel.registerauth_service.dto.RegisterRequest;
import com.rochanahuel.registerauth_service.exception.EmailInUseException;
import com.rochanahuel.registerauth_service.exception.UserNotFoundException;
import com.rochanahuel.registerauth_service.model.User;
import com.rochanahuel.registerauth_service.repository.UserRepository;
import com.rochanahuel.registerauth_service.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> emailInUse = userRepository.findByEmail(request.email());

        if (emailInUse.isPresent()) {
            throw new EmailInUseException("The email entered is already registered");
        }

        User newUser = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .dateOfBirth(request.dateOfBirth())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(newUser);

        var jwtToken = jwtService.generateToken(newUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.email()));

        Map<String, Object> setRole = new HashMap<>();
        setRole.put("Role", user.getRole());

        var jwtToken = jwtService.generateToken(setRole, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
