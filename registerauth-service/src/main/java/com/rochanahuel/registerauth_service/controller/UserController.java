package com.rochanahuel.registerauth_service.controller;

import com.rochanahuel.registerauth_service.dto.UpdateEmailRequest;
import com.rochanahuel.registerauth_service.dto.UserDto;
import com.rochanahuel.registerauth_service.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUserEmail(@PathVariable("email") @Valid UpdateEmailRequest email) {

        return ResponseEntity.ok(userService.updateUserEmail(email));
    }
}
