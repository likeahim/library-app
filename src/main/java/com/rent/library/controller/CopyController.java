package com.rent.library.controller;

import com.rent.library.controller.exception.CopyNotFoundException;
import com.rent.library.controller.exception.TitleNotFoundException;
import com.rent.library.domain.Copy;
import com.rent.library.domain.Status;
import com.rent.library.domain.dto.CopyDto;
import com.rent.library.mapper.CopyMapper;
import com.rent.library.service.CopyService;
import com.rent.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library/copies")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CopyController {

    private final CopyService copyService;
    private final TitleService titleService;
    private final CopyMapper mapper;

    @GetMapping
    public ResponseEntity<List<CopyDto>> getAvailableCopies(
            @RequestParam("titleId") Long titleId) {
        List<Copy> copies = copyService.getCopies(titleId, Status.AVAILABLE);
        return ResponseEntity.ok(mapper.mapToCopyDtoList(copies));
    }

    @GetMapping("/rented")
    public ResponseEntity<List<CopyDto>> getRentedCopies(
            @RequestParam("titleId") Long titleId) {
        List<Copy> copies = copyService.getCopies(titleId, Status.RENTED);
        return ResponseEntity.ok(mapper.mapToCopyDtoList(copies));
    }


    @PostMapping(value = "{titleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createACopy(@RequestBody CopyDto copyDto, @PathVariable Long titleId) throws TitleNotFoundException {
        titleService.getTitle(titleId);
        Copy copy = mapper.mapToCopy(copyDto, titleId);
        copyService.saveCopy(copy);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<CopyDto> updateStatus(
            @RequestParam("status") Status status, @RequestParam("id") Long id) throws CopyNotFoundException {
        Copy copy = copyService.getCopy(id);
        Copy copyChanged = mapper.changeStatus(copy, status);
        Copy savedCopy = copyService.saveCopy(copyChanged);
        return ResponseEntity.ok(mapper.mapToCopyDto(savedCopy));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long id) throws CopyNotFoundException {
        Copy toDelete = copyService.getCopy(id);
        copyService.deleteCopy(toDelete);
        return ResponseEntity.ok().build();
    }
}
