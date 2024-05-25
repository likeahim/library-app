package com.rent.library.controller;

import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.*;
import com.rent.library.domain.dto.UserDto;
import com.rent.library.mapper.UserMapper;
import com.rent.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/library/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(mapper.mapToUserDto(service.getUser(userId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = mapper.mapToUser(userDto);
        service.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        User usertToDelete = service.getUser(userId);
        service.deleteUser(usertToDelete);
        return ResponseEntity.ok().build();
    }
}
