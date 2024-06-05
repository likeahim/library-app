package com.rent.library.mapper;

import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.domain.Title;
import com.rent.library.domain.dto.CopyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyMapper {

    public Copy mapToCopy(final CopyDto copyDto, final Title title) {
        return new Copy(
                copyDto.getId(),
                title,
                Status.valueOf(copyDto.getStatus())
        );
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto (
                copy.getId(),
                copy.getTitleId().getId(),
                copy.getStatus().toString()
        );
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copyList) {
        return copyList.stream()
                .map(this::mapToCopyDto)
                .toList();
    }
}
