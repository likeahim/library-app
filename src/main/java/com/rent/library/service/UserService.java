package com.rent.library.service;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.controller.exception.RentalNotFoundException;
import com.rent.library.controller.exception.TitleNotFoundException;
import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.*;
import com.rent.library.repository.CopyRepository;
import com.rent.library.repository.RentalRepository;
import com.rent.library.repository.TitleRepository;
import com.rent.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TitleRepository titleRepository;
    private final CopyRepository copyRepository;
    private final RentalRepository rentalRepository;

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
