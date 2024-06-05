package com.rent.library.service;

import com.rent.library.controller.exception.TitleNotFoundException;
import com.rent.library.domain.Title;
import com.rent.library.repository.TitleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TitleServiceTestSuite {

    @Autowired
    TitleService titleService;
    @Autowired
    TitleRepository titleRepository;

    @Test
    public void testAddTitle() {
        //Given
        Title title = new Title();

        //When
        titleService.saveTitle(title);

        //Then
        List<Title> allTitles = titleService.getAllTitles();
        assertEquals(1, allTitles.size());
    }
    @Test
    public void testGetTitle() throws TitleNotFoundException {
        //Given
        Title title = new Title();
        titleService.saveTitle(title);

        //When
        Title foundTitle = titleService.getTitle(title.getId());

        //Then
        assertEquals(title.getId(), foundTitle.getId());
    }

    @Test
    public void testDeleteTitle() {
        //Given
        Title title = new Title();
        titleService.saveTitle(title);

        //When
        titleService.deleteTitle(title);

        //Then
        List<Title> allTitles = titleService.getAllTitles();
        assertEquals(0, allTitles.size());
    }
}
