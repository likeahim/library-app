package com.rent.library.controller;

import com.rent.library.domain.*;
import com.rent.library.domain.dto.CopyDto;
import com.rent.library.domain.dto.RentalDto;
import com.rent.library.domain.dto.TitleDto;
import com.rent.library.domain.dto.UserDto;
import com.rent.library.mapper.CopyMapper;
import com.rent.library.mapper.RentalMapper;
import com.rent.library.mapper.TitleMapper;
import com.rent.library.mapper.UserMapper;
import com.rent.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LibraryController {

    private final LibraryService service;
    private final CopyMapper copyMapper;
    private final RentalMapper rentalMapper;
    private final TitleMapper titleMapper;
    private final UserMapper userMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        service.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDto(service.getUser(userId)));
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        User usertToDelete = service.getUser(userId);
        service.deleteUser(usertToDelete);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/titles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTitle(@RequestBody TitleDto titleDto) {
        Title title = titleMapper.mapToTitle(titleDto);
        service.saveTitle(title);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/titles")
    public ResponseEntity<List<TitleDto>> getAllTitles() {
        List<Title> titles = service.getAllTitles();
        return ResponseEntity.ok(titleMapper.mapToTitleDtoList(titles));
    }

    @GetMapping("/copies")
    public ResponseEntity<List<CopyDto>> getAvailableCopies(
            @RequestParam("titleId") Long titleId) {
        List<Copy> copies = service.getCopies(titleId, Status.AVAILABLE);
        return ResponseEntity.ok(copyMapper.mapToCopyDtoList(copies));
    }

    @GetMapping("/copies/rented")
    public ResponseEntity<List<CopyDto>> getRentedCopies(
            @RequestParam("titleId") Long titleId) {
        List<Copy> copies = service.getCopies(titleId, Status.RENTED);
        return ResponseEntity.ok(copyMapper.mapToCopyDtoList(copies));
    }


    @PostMapping(value = "/copies/{titleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createACopy(@RequestBody CopyDto copyDto, @PathVariable Long titleId) throws TitleNotFoundException {
        service.getTitle(titleId);
        Copy copy = copyMapper.mapToCopy(copyDto, titleId);
        service.saveCopy(copy);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/copies")
    public ResponseEntity<CopyDto> updateStatus(
            @RequestParam("status") Status status, @RequestParam("id") Long id) throws CopyNotFoundException {
        Copy copy = service.getCopy(id);
        Copy copyChanged = copyMapper.changeStatus(copy, status);
        Copy savedCopy = service.saveCopy(copyChanged);
        return ResponseEntity.ok(copyMapper.mapToCopyDto(savedCopy));
    }

    @PostMapping(value = "/rent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> rentACopy(@RequestBody RentalDto rentalDto) throws CopyNotFoundException {
        Copy copy = service.getCopy(rentalDto.getCopyId());
        if(copy.getStatus() == Status.AVAILABLE) {
            copy.setStatus(Status.RENTED);
            Rental rental = rentalMapper.mapToRental(rentalDto, copy);
            service.saveRental(rental);
            return ResponseEntity.ok().build();
        } else throw new CopyNotFoundException();
    }

    @DeleteMapping(value = "/rent/close/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) throws RentalNotFoundException {
        Rental rental = service.getRental(id);
        rental.getCopyId().setStatus(Status.AVAILABLE);
        service.deleteRental(rental);
        return ResponseEntity.ok().build();
    }

}
