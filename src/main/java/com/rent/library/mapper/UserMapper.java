package com.rent.library.mapper;

import com.rent.library.domain.User;
import com.rent.library.domain.dto.UserDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserMapper {
    //is list of all readers required or needed?

    public User mapToUser(final UserDto userDto) {
        LocalDate now = LocalDate.now();
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastname(),
                now
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
