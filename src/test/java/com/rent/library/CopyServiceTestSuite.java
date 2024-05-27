package com.rent.library;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.mapper.CopyMapper;
import com.rent.library.repository.CopyRepository;
import com.rent.library.service.CopyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CopyServiceTestSuite {

    @Autowired
    private CopyService copyService;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private CopyMapper copyMapper;

    @Test
    public void testGetCopy() throws CopyNotFoundException {

        //Given
        Copy copy = new Copy();
        copyRepository.save(copy);
        Long id = copy.getId();

        //When
        Copy copy1 = copyService.getCopy(id);

        //Then
        assertEquals(copy.getId(), copy1.getId());
    }

    @Test
    public void testGetListCopy() {
        //Given
        Copy copy = new Copy();
        Long titleId = copy.getTitleId();
        copy.setStatus(Status.AVAILABLE);
        copyRepository.save(copy);

        //When
        List<Copy> copies = copyService.getCopies(titleId, Status.AVAILABLE);

        //Then
        assertEquals(1, copies.size());
    }

    @Test
    public void testAddCopy() {
        //Given
        Copy copy = new Copy(100L, 100L, Status.AVAILABLE);

        //When
        copyRepository.save(copy);

        //Then
        assertEquals(100L, copy.getId());
    }

    @Test
    public void testChangeStatus() {
        //Given
        Copy copy = new Copy(100L, 100L, Status.AVAILABLE);

        //When
        Copy copy1 = copyMapper.changeStatus(copy, Status.DESTROYED);

        //Then
        assertEquals(Status.DESTROYED, copy1.getStatus());
    }

    @Test
    public void testDeleteCopy() {
        //Given
        Copy copy = new Copy();
        Copy copy2 = new Copy();
        copyRepository.save(copy);
        copyRepository.save(copy2);
        Long id = copy.getId();

        //When
        copyRepository.delete(copy);

        //Then
        List<Copy> all = (List<Copy>) copyRepository.findAll();
        assertEquals(1, all.size());
    }

}
