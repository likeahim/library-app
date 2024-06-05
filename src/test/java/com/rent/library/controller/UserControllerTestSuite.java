package com.rent.library.controller;

import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.User;
import com.rent.library.domain.dto.UserDto;
import com.rent.library.mapper.UserMapper;
import com.rent.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTestSuite {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = new User(120L, "Test firstName", "Test lastname", LocalDate.now());
        Long id = user.getId();
        String firstName = user.getFirstName();
        String lastname = user.getLastname();
        LocalDate created = user.getCreated();
        userDto = new UserDto(id, firstName, lastname, created);
    }

    @Test
    public void shouldCreateUser() {
        //Given
        when(userMapper.mapToUser(userDto)).thenReturn(user);

        //When
        ResponseEntity<Void> response = userController.createUser(userDto);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldFetchUser() throws UserNotFoundException {
        //Given
        Long userId = user.getId();
        when(userService.getUser(userId)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        ResponseEntity<UserDto> response = userController.getUser(userId);

        //Then
        assertEquals(response.getBody(), userDto);
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldDeleteUser() throws UserNotFoundException {
        //Given
        Long userId = user.getId();
        when(userService.getUser(userId)).thenReturn(user);

        //When
        ResponseEntity<Void> response = userController.deleteUser(userId);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldThrowException() throws UserNotFoundException {
        //Given
        Long falseId = 300L;

        //When
        when(userService.getUser(falseId)).thenThrow(UserNotFoundException.class);

        //Then
        assertThrows(UserNotFoundException.class, () -> userController.getUser(falseId));
    }
}
