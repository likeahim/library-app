package com.rent.library.controller;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.controller.exception.RentalNotFoundException;
import com.rent.library.controller.exception.UserNotFoundException;
import com.rent.library.domain.Copy;
import com.rent.library.domain.Rental;
import com.rent.library.domain.Status;
import com.rent.library.domain.User;
import com.rent.library.domain.dto.RentalDto;
import com.rent.library.mapper.RentalMapper;
import com.rent.library.service.CopyService;
import com.rent.library.service.RentalService;
import com.rent.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/rentals")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RentalController {

    private final RentalService rentalService;
    private final CopyService copyService;
    private final RentalMapper mapper;
    private final UserService userService;

    @GetMapping(value = "{rentalId}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable Long rentalId) throws RentalNotFoundException {
        Rental rental = rentalService.getRental(rentalId);
        return ResponseEntity.ok(mapper.mapToRentalDto(rental));
    }

    @GetMapping
    public ResponseEntity<List<RentalDto>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(mapper.mapToRentalDtoList(rentals));
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<List<RentalDto>> getAllRentalsForUser(@PathVariable(value = "userId") Long userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        List<Rental> rentalsByUser = rentalService.getRentalsByUser(user);
        return ResponseEntity.ok(mapper.mapToRentalDtoList(rentalsByUser));
    }

    @PostMapping(value = "/rent")
    public ResponseEntity<Void> rentACopy(
            @RequestParam Long copyId, @RequestParam Long userId
    ) throws CopyNotFoundException, UserNotFoundException {
        Rental rental = new Rental();
        Long rentalId = rental.getId();
        Copy copy = copyService.getCopy(copyId);
        User user = userService.getUser(userId);
        Rental rentalToSave = mapper.mapToRental(rentalId, copy, user);
        if(copy.getStatus() == Status.AVAILABLE) {
            copyService.changeStatus(copy, Status.RENTED);
            rentalService.saveRental(rentalToSave);
            return ResponseEntity.ok().build();
        } else throw new CopyNotFoundException();
    }

    //should check if given id references to copy with status RENTED toDo
    @PatchMapping(value = "/return/{id}")
    public ResponseEntity<Void> returnCopy(@PathVariable Long id) throws RentalNotFoundException {
        Rental rental = rentalService.getRental(id);
        copyService.changeStatus(rental.getCopyId(), Status.AVAILABLE);
        rentalService.saveRental(rental);
        return ResponseEntity.ok().build();
    }
}
