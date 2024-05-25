package com.rent.library.service;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyService {

    private final CopyRepository copyRepository;

    public Copy saveCopy(final Copy copy) {
        return copyRepository.save(copy);
    }

    public Copy getCopy(final Long id) throws CopyNotFoundException {
        return copyRepository.findById(id).orElseThrow(CopyNotFoundException::new);
    }

    public List<Copy> getCopies(final Long titleId, final Status status) {
        return copyRepository.findCopiesByTitleIdAndStatus(titleId, status);
    }

    public void deleteCopy(final Copy copy) throws CopyNotFoundException {
        copyRepository.delete(copy);
    }

}
