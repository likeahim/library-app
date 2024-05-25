package com.rent.library.controller;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.controller.exception.RentalNotFoundException;
import com.rent.library.domain.Copy;
import com.rent.library.domain.Rental;
import com.rent.library.domain.Status;
import com.rent.library.domain.dto.RentalDto;
import com.rent.library.mapper.RentalMapper;
import com.rent.library.service.CopyService;
import com.rent.library.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @GetMapping
    public ResponseEntity<List<RentalDto>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(mapper.mapToRentalDtoList(rentals));
    }
    @PostMapping(value = "/rent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> rentACopy(@RequestBody RentalDto rentalDto) throws CopyNotFoundException {
        Copy copy = copyService.getCopy(rentalDto.getCopyId());
        if(copy.getStatus() == Status.AVAILABLE) {
            copy.setStatus(Status.RENTED);
            Rental rental = mapper.mapToRental(rentalDto, copy);
            rentalService.saveRental(rental);
            return ResponseEntity.ok().build();
        } else throw new CopyNotFoundException();
    }

    @DeleteMapping(value = "/return/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) throws RentalNotFoundException {
        Rental rental = rentalService.getRental(id);
        rental.getCopyId().setStatus(Status.AVAILABLE);
        rentalService.deleteRental(rental);
        return ResponseEntity.ok().build();
    }
}
