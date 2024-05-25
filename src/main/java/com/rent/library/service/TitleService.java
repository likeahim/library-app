package com.rent.library.service;

import com.rent.library.controller.exception.TitleNotFoundException;
import com.rent.library.domain.Title;
import com.rent.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public void deleteTitle(final Title title) {
        titleRepository.delete(title);
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Title getTitle(final Long id) throws TitleNotFoundException {
        return titleRepository.findById(id).orElseThrow(TitleNotFoundException::new);
    }
}
