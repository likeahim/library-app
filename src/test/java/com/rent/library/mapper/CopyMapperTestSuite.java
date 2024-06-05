package com.rent.library.mapper;

import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.domain.Title;
import com.rent.library.domain.dto.CopyDto;
import com.rent.library.repository.CopyRepository;
import com.rent.library.repository.TitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CopyMapperTestSuite {

    @Autowired
    private CopyMapper copyMapper;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private TitleRepository titleRepository;

    private Title title;

    @BeforeEach
    void setUp() {
        title = new Title();
        title.setTitle("Test Title");
        title.setAuthor("Test Author");
        title.setPubYear(2000);
        title.setCopies(new ArrayList<>());
        titleRepository.save(title);
    }

    @Test
    public void shouldFetchCopyDto() {
        //Given
        Copy copy = new Copy(100L, title, Status.AVAILABLE);
        copyRepository.save(copy);
        Long copyId = copy.getId();

        //When
        CopyDto copyDto = copyMapper.mapToCopyDto(copy);

        //Then
        Long copyDtoId = copyDto.getId();
        assertEquals(copyId, copyDtoId);
    }

    @Test
    //Given
    public void shouldFetchCopy() {
        //Given
        Long titleId = title.getId();
        CopyDto copyDto = new CopyDto(120L, titleId, Status.AVAILABLE.toString());

        //When
        Copy copy = copyMapper.mapToCopy(copyDto, title);

        //Then
        assertEquals(copyDto.getId(), copy.getId());
        assertEquals(copyDto.getTitleId(), copy.getTitleId().getId());
    }

    @Test
    public void shouldFetchCopyDtoList() {
        //Given
        Copy copy = new Copy();
        copyRepository.save(copy);
        copy.setTitleId(title);
        copy.setStatus(Status.AVAILABLE);
        List<Copy> copies = List.of(copy);

        //When
        List<CopyDto> copyDtos = copyMapper.mapToCopyDtoList(copies);
        Long copyDtoId = copyDtos.get(0).getId();

        //Then
        assertEquals(copies.size(), copyDtos.size());
        assertEquals(copy.getId(), copyDtoId);

    }
}
