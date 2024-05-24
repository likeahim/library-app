package com.rent.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CopyDto {
    private Long id;
    private Long titleId;
    private String status;
}
