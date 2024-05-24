package com.rent.library.mapper;

import com.rent.library.domain.User;
import com.rent.library.domain.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    //is list of all readers required or needed?

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastname(),
                userDto.getCreated()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastname(),
                user.getCreated()
        );
    }
}
