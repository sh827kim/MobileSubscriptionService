package com.spark.toy.exception.advice;

import com.spark.toy.dto.ErrorResponseDto;
import com.spark.toy.exception.CustomNoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(value = CustomNoSuchElementException.class)
    public ResponseEntity noSuchElementException(CustomNoSuchElementException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
    }
}
