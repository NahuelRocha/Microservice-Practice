package com.rochanahuel.registerauth_service.service.impl;


import com.rochanahuel.registerauth_service.config.jwtService.JwtService;
import com.rochanahuel.registerauth_service.dto.UpdateEmailRequest;
import com.rochanahuel.registerauth_service.dto.UserDto;
import com.rochanahuel.registerauth_service.exception.EmailInUseException;
import com.rochanahuel.registerauth_service.exception.UserNotFoundException;
import com.rochanahuel.registerauth_service.mapper.Mappers;
import com.rochanahuel.registerauth_service.model.User;
import com.rochanahuel.registerauth_service.repository.UserRepository;
import com.rochanahuel.registerauth_service.service.UserService;
import com.rochanahuel.registerauth_service.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mappers mappers;
    private final JwtService jwtService;

    @Override
    public UserDto getUserById(Long id) {

        User findUser = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));

        return mappers.userToUserDto(findUser);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> findAllUsers = userRepository.findAll();

        return findAllUsers.stream()
                .map(mappers::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteUserById(Long id) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.deleteById(id);

        return "Deleted user";
    }

    public String promoteEmployeeToAdmin(String email) {

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (findUser.getRole().equals(Role.ADMIN)) {
            return "The user is already an administrator";
        }

        findUser.setRole(Role.ADMIN);

        userRepository.save(findUser);

        return "SUCCESS";
    }

    @Override
    public UserDto updateUserEmail(UpdateEmailRequest email) {

        Optional<User> emailAvailable = userRepository.findByEmail(email.email());

        if (emailAvailable.isPresent()){
            throw new EmailInUseException("The email is in use");
        }

        String auth = SecurityContextHolder.getContext().getAuthentication().getName();


        var findUser = userRepository.findByEmail(auth)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        findUser.setEmail(email.email());

        jwtService.generateToken(findUser);

        userRepository.save(findUser);

        return mappers.userToUserDto(findUser);
    }

}
