package com.rent.library.mapper;

import com.rent.library.domain.Copy;
import com.rent.library.domain.Rental;
import com.rent.library.domain.dto.RentalDto;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public Rental mapToRental(final RentalDto rentalDto, final Copy copy) {
        return new Rental(
                rentalDto.getId(),
                rentalDto.getUserId(),
                copy,
                rentalDto.getRentDate(),
                rentalDto.getReturnDate()
        );
    }

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getUserId(),
                rental.getCopyId().getId(),
                rental.getRentDate(),
                rental.getReturnDate()
        );
    }
}
