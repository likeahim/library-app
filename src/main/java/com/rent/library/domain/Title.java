package com.rent.library.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //for tests
@Entity(name = "titles")
public class Title {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "pub_year")
    private int pubYear;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "titleId",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> copies = new ArrayList<>();
}
