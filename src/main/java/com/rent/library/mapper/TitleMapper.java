package com.rent.library.mapper;

import com.rent.library.domain.Title;
import com.rent.library.domain.dto.TitleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getTitle(),
                titleDto.getAuthor(),
                titleDto.getPubYear(),
                titleDto.getCopies()
        );
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getTitle(),
                title.getAuthor(),
                title.getPubYear(),
                title.getCopies()
        );
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titles) {
        return titles.stream()
                .map(this::mapToTitleDto)
                .toList();
    }
}
