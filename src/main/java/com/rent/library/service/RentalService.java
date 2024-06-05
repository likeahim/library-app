package com.rent.library.service;

import com.rent.library.controller.exception.RentalNotFoundException;
import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.Rental;
import com.rent.library.domain.User;
import com.rent.library.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
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

    public List<Rental> getRentalsByUser(final User user) throws UserNotFoundException {
        return rentalRepository.findByUserId(user);
    }
}
