package com.rochanahuel.registerauth_service.service;

import com.rochanahuel.registerauth_service.dto.UpdateEmailRequest;
import com.rochanahuel.registerauth_service.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    String deleteUserById(Long id);

    String promoteEmployeeToAdmin(String email);

    UserDto updateUserEmail(UpdateEmailRequest email);
}
