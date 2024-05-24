package com.rent.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class RentalDto {

    private Long id;
    private Long userId;
    private Long copyId;
    private LocalDate rentDate;
    private LocalDate returnDate;
}
