package com.rent.library.controller;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.controller.exception.TitleNotFoundException;
import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.domain.Title;
import com.rent.library.domain.dto.CopyDto;
import com.rent.library.mapper.CopyMapper;
import com.rent.library.service.CopyService;
import com.rent.library.service.TitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CopyControllerTestSuite {

    @InjectMocks
    private CopyController copyController;
    @Mock
    private CopyService copyService;
    @Mock
    private TitleService titleService;
    @Mock
    private CopyMapper copyMapper;

    private Copy copy;
    private Title title;
    private CopyDto copyDto;
    private Long titleId;
    @BeforeEach
    public void setUp() {
        titleId = 1L;
        title = new Title();
        copy = new Copy();
        copyDto = new CopyDto(1L, titleId, Status.AVAILABLE.toString());
    }
    @Test
    public void shouldFetchAvailableCopies() throws TitleNotFoundException {
        //Given
        List<Copy> copies = List.of(copy);
        List<CopyDto> copiesDto = List.of(copyDto);
        when(titleService.getTitle(titleId)).thenReturn(title);
        when(copyService.getCopies(title, Status.AVAILABLE)).thenReturn(copies);
        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copiesDto);

        //When
        ResponseEntity<List<CopyDto>> response = copyController.getAvailableCopies(titleId);

        //Then
        assertEquals(response.getBody(), copiesDto);
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldCreateCopy() throws TitleNotFoundException {
        //Given
        when(titleService.getTitle(titleId)).thenReturn(title);
        when(copyMapper.mapToCopy(copyDto, title)).thenReturn(copy);

        //When
        ResponseEntity<Void> response = copyController.createACopy(copyDto, titleId);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldChangeStatus() throws CopyNotFoundException {
        //Given
        Long id = copy.getId();
        when(copyService.getCopy(id)).thenReturn(copy);
        Copy rentedCopy = new Copy(1L, title, Status.RENTED);
        when(copyService.changeStatus(copy, Status.RENTED)).thenReturn(rentedCopy);
        copyDto = new CopyDto(1L, titleId, Status.RENTED.toString());

        //When
        ResponseEntity<CopyDto> response = copyController.updateStatus(Status.RENTED, id);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
        assertEquals(copyDto.getStatus(), Status.RENTED.toString());
    }

    @Test
    public void shouldDeleteCopy() throws CopyNotFoundException {
        //Given
        Long id = copy.getId();
        when(copyService.getCopy(id)).thenReturn(copy);

        //When
        ResponseEntity<Void> response = copyController.deleteCopy(id);

        //Then
        assertEquals(response.getStatusCode().toString(), "200 OK");
    }

    @Test
    public void shouldThrowException() throws CopyNotFoundException {
        //Given
        Long falseId = 200L;

        //When
        when(copyService.getCopy(falseId)).thenThrow(CopyNotFoundException.class);

        //Then
        assertThrows(CopyNotFoundException.class, () -> copyController.deleteCopy(falseId));
    }
}
