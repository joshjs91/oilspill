package com.josh.smart.oilspill.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.josh.smart.oilspill.exception.InvalidInstructionException;
import com.josh.smart.oilspill.model.CleaningRequest;
import com.josh.smart.oilspill.model.CleaningResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OilCleanupServiceTest {

    private final OilCleanupService oilCleanupService = new OilCleanupService();

    @Test
    void noPatchesCleanedAndNoMovementWhenZeroInstructions() {
        CleaningRequest cleaningRequestDto = new CleaningRequest();
        cleaningRequestDto.setAreaSize(new Integer[] {2, 2});
        Integer[] expectedStartingPosition = new Integer[] {0, 0};
        cleaningRequestDto.setStartingPosition(expectedStartingPosition);
        Set<Integer[]> oilPatches = new HashSet<>(Arrays.asList(new Integer[] {1, 2}, new Integer[] {0, 2}));
        cleaningRequestDto.setOilPatches(oilPatches);

        CleaningResponse response = oilCleanupService.cleanup(cleaningRequestDto);

        assertThat(response.getFinalPosition()).isEqualTo(expectedStartingPosition);
        assertThat(response.getOilPatchesCleaned()).isEqualTo(0);
    }

    @Test
    void movesToExpectedPositionWithMultipleInstructions() {
        CleaningRequest cleaningRequestDto = new CleaningRequest();
        cleaningRequestDto.setAreaSize(new Integer[] {5, 5});
        cleaningRequestDto.setStartingPosition(new Integer[] {1, 1});
        cleaningRequestDto.setOilPatches(new HashSet<>(Collections.emptyList()));
        cleaningRequestDto.setNavigationInstructions("NESNESW");

        CleaningResponse response = oilCleanupService.cleanup(cleaningRequestDto);

        assertThat(response.getFinalPosition()).isEqualTo(new Integer[] {2, 1});
    }

    @Test
    void clearsAllPatchesThatWeMovedOntoByCleaner() {
        CleaningRequest cleaningRequestDto = new CleaningRequest();
        cleaningRequestDto.setAreaSize(new Integer[] {5, 5});
        cleaningRequestDto.setStartingPosition(new Integer[] {1, 1});
        cleaningRequestDto.setOilPatches(new HashSet<>(Lists.list(new Integer[] {2, 2}, new Integer[] {2, 1}, new Integer[] {3, 3})));
        cleaningRequestDto.setNavigationInstructions("NESNESW");

        CleaningResponse response = oilCleanupService.cleanup(cleaningRequestDto);

        assertThat(response.getFinalPosition()).isEqualTo(new Integer[] {2, 1});
        assertThat(response.getOilPatchesCleaned()).isEqualTo(2);
    }

    @Test
    void throwsExceptionWhenInstructionIsInvalid() {
        CleaningRequest cleaningRequestDto = new CleaningRequest();
        cleaningRequestDto.setAreaSize(new Integer[] {5, 5});
        cleaningRequestDto.setStartingPosition(new Integer[] {1, 1});
        cleaningRequestDto.setOilPatches(new HashSet<>(Collections.emptyList()));
        cleaningRequestDto.setNavigationInstructions("G");

        assertThrows(InvalidInstructionException.class, () -> oilCleanupService.cleanup(cleaningRequestDto));

    }

}
