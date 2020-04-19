package com.josh.smart.oilspill.service;

import com.josh.smart.oilspill.exception.InvalidInstructionException;
import com.josh.smart.oilspill.model.CleaningRequest;
import com.josh.smart.oilspill.model.CleaningResponse;
import com.josh.smart.oilspill.model.Instruction;
import com.josh.smart.oilspill.model.OilSpillMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OilCleanupService {

    public CleaningResponse cleanup(CleaningRequest cleanRequest) {
        OilSpillMap map = initialiseMap(cleanRequest);

        int originalOilPatchCount = map.getOilPatches().size();

        String[] instructions = getInstructions(cleanRequest.getNavigationInstructions());
        for (String instructionString : instructions) {
            Instruction instruction = getInstruction(instructionString);
            map.moveCleanerInDirection(instruction);
        }

        Integer cleanedOilPatchCount = originalOilPatchCount - map.getOilPatches().size();
        Integer[] cleanerPosition = new Integer[] {map.getCleanerPosition().x, map.getCleanerPosition().y};
        return new CleaningResponse(cleanerPosition, cleanedOilPatchCount);
    }

    private OilSpillMap initialiseMap(CleaningRequest cleanRequest) {
        Integer[] size = cleanRequest.getAreaSize();
        log.info("Initialising map of size {}", size);
        Point mapLimits = new Point(size[0], size[1]);

        Integer[] startingPosition = cleanRequest.getStartingPosition();
        Point cleanerStartingPoint = new Point(startingPosition[0], startingPosition[1]);

        Set<Point> oilPatches = cleanRequest.getOilPatches().stream().map(point -> new Point(point[0], point[1])).collect(Collectors.toSet());

        return new OilSpillMap(mapLimits, oilPatches, cleanerStartingPoint);
    }

    private String[] getInstructions(String instructionsString) {
        return instructionsString != null ? instructionsString.split("") : new String[] {};
    }

    private Instruction getInstruction(String instructionString) {
        try {
            return Instruction.valueOf(instructionString);
        } catch (IllegalArgumentException e) {
            log.error("Could not translate instruction provided to a valid instruction.");
            throw new InvalidInstructionException("The provided instruction: " + instructionString + " is not supported");
        }
    }

}
