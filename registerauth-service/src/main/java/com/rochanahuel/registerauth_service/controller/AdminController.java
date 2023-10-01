package com.rochanahuel.registerauth_service.controller;

import com.rochanahuel.registerauth_service.dto.UserDto;
import com.rochanahuel.registerauth_service.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @PutMapping("/promote-to-admin/{email}")
    public ResponseEntity<String> promoteEmployeeToAdmin(@PathVariable String email) {

        return ResponseEntity.ok(userService.promoteEmployeeToAdmin(email));
    }

}
