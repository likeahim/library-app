package com.rent.library.service;

import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.*;
import com.rent.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public User getUser(final Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(final User user) {
        userRepository.delete(user);
    }








}
