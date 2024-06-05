package com.rent.library.mapper;

import com.rent.library.domain.Title;
import com.rent.library.domain.dto.TitleDto;
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
public class TitleMapperTestSuite {

    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void shouldFetchTitleDto() {
        //Given
        Title title = getTitle();
        String titleAuthor = title.getAuthor();
        Long titleId = title.getId();

        //When
        TitleDto titleDto = titleMapper.mapToTitleDto(title);

        //Then
        assertEquals(titleId, titleDto.getId());
        assertEquals(titleAuthor, titleDto.getAuthor());
        assertEquals(title.getCopies().size(), titleDto.getCopies().size());
    }

    @Test
    public void shouldFetchTitle() {
        //Given
        TitleDto titleDto = new TitleDto(150L, "Test title", "Test author", 2000, new ArrayList<>());

        //When
        Title title = titleMapper.mapToTitle(titleDto);

        //Then
        assertEquals(title.getId(), titleDto.getId());
        assertEquals(title.getTitle(), titleDto.getTitle());
        assertEquals(title.getCopies().size(), titleDto.getCopies().size());
    }

    @Test
    public void shouldFetchTitleDtoList() {
        //Given
        Title title = getTitle();
        List<Title> titles = List.of(title);

        //When
        List<TitleDto> titleDtos = titleMapper.mapToTitleDtoList(titles);

        //Then
        assertEquals(titles.size(), titleDtos.size());
        assertEquals(title.getId(), titleDtos.get(0).getId());

    }

    private Title getTitle() {
        Title title = new Title();
        title.setTitle("Test Title");
        title.setAuthor("Test Author");
        title.setPubYear(2000);
        title.setCopies(new ArrayList<>());
        titleRepository.save(title);
        return title;
    }
}
