package ru.coursework_3_bidsystem.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler
    public ResponseEntity<?> handlerHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        return new ResponseEntity<>("Lot data is not readable", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerConstraintViolationException(ConstraintViolationException constraintViolationException) {
        return new ResponseEntity<>("Field constraint violation", HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("Значение поля находится за пределами", HttpStatus.INTERNAL_SERVER_ERROR);Constraint violation
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNoSuchElementException(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>("Lot is not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerNotAcceptableStatusException(NotAcceptableStatusException notAcceptableStatusException) {
        return new ResponseEntity<>("Not acceptable lot status", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<?> handlerBidsNotFoundException(BidsNotFoundException bidsNotFoundException) {
        return new ResponseEntity<>("Bids not found", HttpStatus.NOT_FOUND);
    }
}
