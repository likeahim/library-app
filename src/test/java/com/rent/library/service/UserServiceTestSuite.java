package com.rent.library.service;

import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.User;
import com.rent.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTestSuite {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {
        //Given
        User user1 = new User(100L, "Marc", "Zucker", LocalDate.of(2021, 12, 12));

        //When
        userService.saveUser(user1);

        //Then
        Long id = user1.getId();
        assertEquals(100L, id);
    }

    @Test
    public void testGetUser() throws UserNotFoundException {
        //Given
        User user1 = new User();
        userService.saveUser(user1);
        Long id = user1.getId();

        //When
        User foundUser = userService.getUser(id);

        //Then
        assertEquals(user1.getId(), foundUser.getId());
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        //Given
        User user1 = new User(102L, "Steve", "Getter", LocalDate.of(2021, 10, 10));
        userService.saveUser(user1);

        //When
        userService.deleteUser(user1);

        //Then
        assertThrows(UserNotFoundException.class, () -> userService.getUser(102L));
    }
}
