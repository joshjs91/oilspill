package com.josh.smart.oilspill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import com.josh.smart.oilspill.model.CleaningRequest;
import com.josh.smart.oilspill.model.CleaningResponse;
import com.josh.smart.oilspill.model.Error;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OilSpillApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void cleanRequestsResultsInExpectedLocationAndCleanedPatches() {
        String url = "http://localhost:8080/cleanup";

        CleaningRequest request = new CleaningRequest();
        request.setAreaSize(new Integer[] {6, 5});
        request.setStartingPosition(new Integer[] {2, 0});
        request.setOilPatches(new HashSet<>(Lists.list(new Integer[] {1, 1},
                                                       new Integer[] {1, 3},
                                                       new Integer[] {2, 3},
                                                       new Integer[] {2, 4},
                                                       new Integer[] {4, 3},
                                                       new Integer[] {4, 1})));

        request.setNavigationInstructions("NWNNEEESSS");

        ResponseEntity<CleaningResponse> responseEntity = testRestTemplate.postForEntity(url, request, CleaningResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        CleaningResponse response = responseEntity.getBody();
        assertThat(response.getFinalPosition()).isEqualTo(new Integer[] {4, 0});
        assertThat(response.getOilPatchesCleaned()).isEqualTo(5);

    }

    @Test
    void errorIsReturnedWhenInstructionExceedsBoundary() {
        String url = "http://localhost:8080/cleanup";

        CleaningRequest request = new CleaningRequest();
        request.setAreaSize(new Integer[] {6, 5});
        request.setStartingPosition(new Integer[] {5, 0});
        request.setOilPatches(new HashSet<>(Lists.list(new Integer[] {1, 1}, new Integer[] {1, 3})));

        request.setNavigationInstructions("EE");

        ResponseEntity<Error> responseEntity = testRestTemplate.postForEntity(url, request, Error.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);

        Error response = responseEntity.getBody();
        assertThat(response.getMessage()).isEqualTo("Could not make movement: E due to exceeding map boundary");
        assertThat(response.getCode()).isEqualTo("invalid.instruction.provided");

    }

    @Test
    void errorIsReturnedWhenInstructionIsNotValid() {
        String url = "http://localhost:8080/cleanup";

        CleaningRequest request = new CleaningRequest();
        request.setAreaSize(new Integer[] {6, 5});
        request.setStartingPosition(new Integer[] {2, 0});
        request.setOilPatches(new HashSet<>(Lists.list(new Integer[] {1, 1}, new Integer[] {1, 3})));

        request.setNavigationInstructions("H");

        ResponseEntity<Error> responseEntity = testRestTemplate.postForEntity(url, request, Error.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);

        Error response = responseEntity.getBody();
        assertThat(response.getMessage()).isEqualTo("The provided instruction: H is not supported");
        assertThat(response.getCode()).isEqualTo("invalid.instruction.provided");

    }
}
