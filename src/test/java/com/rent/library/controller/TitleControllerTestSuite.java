package com.rent.library.controller;

import com.rent.library.controller.exception.TitleNotFoundException;
import com.rent.library.domain.Title;
import com.rent.library.domain.dto.TitleDto;
import com.rent.library.mapper.TitleMapper;
import com.rent.library.service.TitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TitleControllerTestSuite {

    @InjectMocks
    private TitleController titleController;
    @Mock
    private TitleService titleService;
    @Mock
    private TitleMapper titleMapper;

    private Title title;
    private TitleDto titleDto;
    @BeforeEach
    void setUp() {
        title = new Title(100L, "Test title", "Test author", 2000, new ArrayList<>());
        titleDto = new TitleDto(100L, "Test title", "Test author", 2000, new ArrayList<>());
    }

    @Test
    public void shouldFetchAllTitles() {
        //Given
        List<Title> titles = List.of(title);
        List<TitleDto> titlesDtos = List.of(titleDto);
        when(titleService.getAllTitles()).thenReturn(titles);
        when(titleMapper.mapToTitleDtoList(titles)).thenReturn(titlesDtos);

        //When
        ResponseEntity<List<TitleDto>> response = titleController.getAllTitles();

        //Then
        assertEquals(response.getBody(), titlesDtos);
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldFetchTitleById() throws TitleNotFoundException {
        //Given
        Long id = title.getId();
        when(titleService.getTitle(id)).thenReturn(title);
        when(titleMapper.mapToTitleDto(title)).thenReturn(titleDto);

        //When
        ResponseEntity<TitleDto> response = titleController.getTitleById(id);

        //Then
        assertEquals(response.getBody(), titleDto);
        assertEquals(response.getStatusCode().toString(), "200 OK");
        assertEquals(id, titleDto.getId());
    }

    @Test
    public void shouldCreateTitle() {
        //Given
        when(titleMapper.mapToTitle(titleDto)).thenReturn(title);

        //When
        ResponseEntity<Void> response = titleController.createTitle(titleDto);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldThrowException() throws TitleNotFoundException {
        //Given
        Long falseId = 250L;

        //When
        when(titleService.getTitle(falseId)).thenThrow(TitleNotFoundException.class);

        //Then
        assertThrows(TitleNotFoundException.class, () -> titleController.getTitleById(falseId));
    }
}
