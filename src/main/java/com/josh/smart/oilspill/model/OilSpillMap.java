package com.josh.smart.oilspill.model;

import com.josh.smart.oilspill.exception.InvalidInstructionException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Set;

@Getter
@Slf4j
public class OilSpillMap {

    private final Point limit;

    private final Set<Point> oilPatches;

    private Point cleanerPosition;

    public OilSpillMap(Point limit, Set<Point> oilPatches, Point cleanerPosition) {

        oilPatches.remove(cleanerPosition);
        this.limit = limit;
        this.oilPatches = oilPatches;
        this.cleanerPosition = cleanerPosition;
    }

    public OilSpillMap moveCleanerInDirection(Instruction instruction) {
        log.info("Handling instruction {} from position {}", instruction, this.cleanerPosition);

        int newCleanerXCoord = this.cleanerPosition.x + instruction.xMovement;
        int newCleanerYCoord = this.cleanerPosition.y + instruction.yMovement;

        if (newPositionWithinBoundary(newCleanerXCoord, newCleanerYCoord)) {
            log.error("Could not make instruction because it exceeds the boundary");
            throw new InvalidInstructionException("Could not make movement: " + instruction + " due to exceeding map boundary");
        }

        Point newPosition = new Point(newCleanerXCoord, newCleanerYCoord);
        this.cleanerPosition = newPosition;
        cleanPatch(newPosition);

        return this;
    }

    private boolean newPositionWithinBoundary(int newCleanerXCoord, int newCleanerYCoord) {
        return newCleanerXCoord > limit.x || newCleanerXCoord < 0 || newCleanerYCoord > limit.y || newCleanerYCoord < 0;
    }

    private void cleanPatch(Point patchToClean) {
        boolean cleaned = this.oilPatches.remove(patchToClean);
        log.info("Cleaning patch {}, oil cleaned {}", patchToClean, cleaned);
    }

}
