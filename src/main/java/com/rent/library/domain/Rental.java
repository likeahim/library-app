package com.rent.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "copy_id")
    private Copy copyId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "rent_date")
    private LocalDate rentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "return_date")
    private LocalDate returnDate;

}
