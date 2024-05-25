package com.rent.library.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with this id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CopyNotFoundException.class)
    public ResponseEntity<Object> handleCopyNotFoundException(CopyNotFoundException exception) {
        return new ResponseEntity<>("No such a copy in database", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TitleNotFoundException.class)
    public ResponseEntity<Object> handleTitleNotFoundException(TitleNotFoundException exception) {
        return new ResponseEntity<>("There is no such a title in database", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<Object> handleRentalNotFoundException(RentalNotFoundException exception) {
        return new ResponseEntity<>("There is no such a rental in database", HttpStatus.BAD_REQUEST);
    }

}
