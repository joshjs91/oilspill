package com.josh.smart.oilspill;

import static org.assertj.core.api.Assertions.assertThat;

import com.josh.smart.oilspill.controller.OilCleanupAdvice;
import com.josh.smart.oilspill.exception.InvalidInstructionException;
import com.josh.smart.oilspill.model.Error;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OilCleanupAdviceTest {

    @Test
    void convertsExceptionToResponseEntity() {
        OilCleanupAdvice oilCleanupAdvice = new OilCleanupAdvice();
        ResponseEntity<Error> responseEntity = oilCleanupAdvice.handle(new InvalidInstructionException("hello"));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Error expectedError = new Error("hello", "invalid.instruction.provided");
        assertThat(responseEntity.getBody()).isEqualToComparingFieldByField(expectedError);
    }
}
