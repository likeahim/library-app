package com.rent.library.domain.dto;

import com.rent.library.domain.Copy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TitleDto {
    private Long id;
    private String title;
    private String author;
    private int pubYear;
    private List<Copy> copies;
}
