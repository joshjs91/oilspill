package com.josh.smart.oilspill.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.josh.smart.oilspill.exception.InvalidInstructionException;
import com.josh.smart.oilspill.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OilCleanupAdvice {

    public static final String INVALID_INSTRUCTION_PROVIDED_CODE = "invalid.instruction.provided";

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(InvalidInstructionException.class)
    public ResponseEntity<Error> handle(InvalidInstructionException exception) {
        Error error = new Error(exception.getMessage(), INVALID_INSTRUCTION_PROVIDED_CODE);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }


}
