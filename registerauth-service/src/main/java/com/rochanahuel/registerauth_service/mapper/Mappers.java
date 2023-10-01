package com.rochanahuel.registerauth_service.mapper;

import com.rochanahuel.registerauth_service.dto.UserDto;
import com.rochanahuel.registerauth_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mappers {

    public UserDto userToUserDto(User user) {

        return new UserDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole()
        );
    }

}
