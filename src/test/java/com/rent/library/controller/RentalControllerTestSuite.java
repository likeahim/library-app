package com.rent.library.controller;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.controller.exception.RentalNotFoundException;
import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.*;
import com.rent.library.domain.dto.RentalDto;
import com.rent.library.mapper.RentalMapper;
import com.rent.library.service.CopyService;
import com.rent.library.service.RentalService;
import com.rent.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalControllerTestSuite {

    @InjectMocks
    private RentalController rentalController;
    @Mock
    private RentalService rentalService;
    @Mock
    private RentalMapper rentalMapper;
    @Mock
    private CopyService copyService;
    @Mock
    private UserService userService;
    private Rental rental;
    private RentalDto rentalDto;
    private Copy copy;
    private User user;

    @BeforeEach
    public void setUp() {
        Title title = new Title(130L, "Test title", "Test author", 2000, new ArrayList<>());
        copy = new Copy(130L, title, Status.AVAILABLE);
        user = new User(140L, "Test firstName", "Test lastname", LocalDate.now());
        rental = new Rental(150L, user, copy, LocalDate.now(), LocalDate.now().plusDays(30));
        rentalDto = new RentalDto(
                rental.getId(),
                rental.getUserId().getId(),
                rental.getCopyId().getId(),
                rental.getRentDate(),
                rental.getReturnDate()
        );
    }

    //RentalController rentACopy() to rebuild, because mapToRental has null argument
    @Test
    public void shouldCreateRental() throws CopyNotFoundException, UserNotFoundException {
        //Given
        when(copyService.getCopy(copy.getId())).thenReturn(copy);
        when(userService.getUser(user.getId())).thenReturn(user);
        when(rentalMapper.mapToRental(null, copy, user)).thenReturn(rental);
        //When
        ResponseEntity<Void> response = rentalController.rentACopy(copy.getId(), user.getId());
        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldFetchRentalById() throws RentalNotFoundException {
        //Given
        Long id = rental.getId();
        when(rentalService.getRental(id)).thenReturn(rental);
        when(rentalMapper.mapToRentalDto(rental)).thenReturn(rentalDto);
        //When
        ResponseEntity<RentalDto> response = rentalController.getRentalById(id);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
        assertEquals(response.getBody(), rentalDto);
    }

    @Test
    public void shouldFetchAllRentals() throws RentalNotFoundException {
        //Given
        List<Rental> rentals = List.of(rental);
        List<RentalDto> rentalDtos = List.of(rentalDto);
        when(rentalService.getAllRentals()).thenReturn(rentals);
        when(rentalMapper.mapToRentalDtoList(rentals)).thenReturn(rentalDtos);

        //When
        ResponseEntity<List<RentalDto>> response = rentalController.getAllRentals();

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
        assertEquals(response.getBody(), rentalDtos);
    }

    //how to build the test without null - check getRentalsByUserId in RentalController
    @Test
    public void shouldFetchAllRentalsByUser() throws UserNotFoundException {
        //Given
        List<Rental> rentals = List.of(rental);
        List<RentalDto> rentalsDtos = List.of(rentalDto);
        when(rentalService.getRentalsByUser(null)).thenReturn(rentals);
        when(rentalMapper.mapToRentalDtoList(rentals)).thenReturn(rentalsDtos);

        //When
        ResponseEntity<List<RentalDto>> response = rentalController.getAllRentalsForUser(null);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
        assertEquals(response.getBody(), rentalsDtos);
    }

    //should changeStatus be type Copy, or void?
    @Test
    public void shouldReturnCopy() throws RentalNotFoundException {
        //Given
        Long id = rental.getId();
        copyService.changeStatus(copy, Status.RENTED);
        when(rentalService.getRental(id)).thenReturn(rental);
        when(copyService.changeStatus(copy, Status.AVAILABLE)).thenReturn(copy);

        //When
        ResponseEntity<Void> response = rentalController.returnCopy(id);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
        assertEquals(copy.getStatus(), Status.AVAILABLE);
    }
}
