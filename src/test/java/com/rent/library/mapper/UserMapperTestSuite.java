package com.rent.library.mapper;

import com.rent.library.domain.User;
import com.rent.library.domain.dto.UserDto;
import com.rent.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserMapperTestSuite {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldFetchUserDto() {
        //Given
        User user = new User(200L, "Jan", "Koval", LocalDate.now());
        userRepository.save(user);

        //When
        UserDto userDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(user.getId(), userDto.getId());
        assertEquals(userDto.getLastname(), "Koval");
    }

    @Test
    public void shouldFetchUser() {
        //Given
        UserDto userDto = new UserDto(250L, "Jamal", "Nowak", LocalDate.now());

        //When
        User user = userMapper.mapToUser(userDto);

        //Then
        assertEquals(userDto.getId(), user.getId());
        assertEquals(LocalDate.of(2024, 6, 5), user.getCreated());
        assertEquals("Nowak", user.getLastname());
    }
}
