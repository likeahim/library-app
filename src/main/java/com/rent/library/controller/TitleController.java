package com.rent.library.controller;

import com.rent.library.domain.Title;
import com.rent.library.domain.dto.TitleDto;
import com.rent.library.mapper.TitleMapper;
import com.rent.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/titles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TitleController {

    private final TitleService service;
    private final TitleMapper mapper;

    @GetMapping
    public ResponseEntity<List<TitleDto>> getAllTitles() {
        List<Title> titles = service.getAllTitles();
        return ResponseEntity.ok(mapper.mapToTitleDtoList(titles));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTitle(@RequestBody TitleDto titleDto) {
        Title title = mapper.mapToTitle(titleDto);
        service.saveTitle(title);
        return ResponseEntity.ok().build();
    }
}
