package com.rent.library.service;

import com.rent.library.controller.exception.RentalNotFoundException;
import com.rent.library.domain.Rental;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RentalServiceTestSuite {

    @Autowired
    private RentalService rentalService;

    @Test
    public void testAddRental() {
        //Given
        Rental rental = new Rental();

        //When
        rentalService.saveRental(rental);

        //Then
        assertNotNull(rental);
    }

    @Test
    public void testFindRentalById() throws RentalNotFoundException {
        //Given
        Rental rental = new Rental();
        rentalService.saveRental(rental);
        Long id = rental.getId();

        //When
        Rental rental1 = rentalService.getRental(id);

        //Then
        assertNotNull(rental1);
    }

    @Test
    public void testFindAllRentals() {
        //Given
        Rental rental = new Rental();
        rentalService.saveRental(rental);

        //When
        List<Rental> allRentals = rentalService.getAllRentals();

        //Then
        assertEquals(1, allRentals.size());
    }

    @Test
    public void testDeleteRental() throws RentalNotFoundException {
        //Given
        Rental rental = new Rental();
        rentalService.saveRental(rental);
        Long id = rental.getId();

        //When
        rentalService.deleteRental(rental);

        //Then
        List<Rental> allRentals = rentalService.getAllRentals();
        assertEquals(0, allRentals.size());
    }
}
