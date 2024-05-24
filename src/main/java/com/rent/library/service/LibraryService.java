package com.rent.library.service;

import com.rent.library.controller.CopyNotFoundException;
import com.rent.library.controller.RentalNotFoundException;
import com.rent.library.controller.TitleNotFoundException;
import com.rent.library.controller.UserNotFoundException;
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
public class LibraryService {

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

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public void deleteTitle(final Title title) {
        titleRepository.delete(title);
    }

    public Copy saveCopy(final Copy copy) {
        return copyRepository.save(copy);
    }

    public Copy getCopy(final Long id) throws CopyNotFoundException {
        return copyRepository.findById(id).orElseThrow(CopyNotFoundException::new);
    }

    public List<Copy> getCopies(final Long titleId, final Status status) {
        return copyRepository.findCopiesByTitleIdAndStatus(titleId, status);
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Title getTitle(final Long id) throws TitleNotFoundException {
        return titleRepository.findById(id).orElseThrow(TitleNotFoundException::new);
    }

    public Rental saveRental(final Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRental(final Rental rental) {
        rentalRepository.delete(rental);
    }

    public Rental getRental(final Long id) throws RentalNotFoundException {
        return rentalRepository.findById(id).orElseThrow(RentalNotFoundException::new);
    }
}
