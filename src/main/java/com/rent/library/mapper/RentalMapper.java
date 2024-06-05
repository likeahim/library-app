package com.rent.library.mapper;

import com.rent.library.domain.Copy;
import com.rent.library.domain.Rental;
import com.rent.library.domain.User;
import com.rent.library.domain.dto.RentalDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalMapper {

    public Rental mapToRental(final Long rentalId, final Copy copy, final User user) {
        LocalDate rentDate = LocalDate.now();
        return new Rental(
                rentalId,
                user,
                copy,
                rentDate,
                rentDate.plusDays(30)
        );
    }

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getUserId().getId(),
                rental.getCopyId().getId(),
                rental.getRentDate(),
                rental.getReturnDate()
        );
    }

    public List<RentalDto> mapToRentalDtoList(List<Rental> rentals) {
        return rentals.stream()
                .map(this::mapToRentalDto)
                .toList();
    }
}
