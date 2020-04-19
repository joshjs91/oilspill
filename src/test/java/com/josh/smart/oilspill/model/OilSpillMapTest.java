package com.josh.smart.oilspill.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OilSpillMapTest {

    //TODO test that it cleans up the oil

    //TODO test that move direction moves

    @Test
    void movesCleanerUpForNorthInstruction() {
        Point grid = new Point(2, 2);
        Set<Point> oilPatches = new HashSet<>(Collections.emptyList());
        Point cleanerPosition = new Point(1, 1);
        OilSpillMap map = new OilSpillMap(grid, oilPatches, cleanerPosition);

        map.moveCleanerInDirection(Instruction.N);

        assertThat(map.getCleanerPosition()).isEqualTo(new Point(1, 2));
    }

    @Test
    void movesCleanerDownForSouthInstruction() {
        Point grid = new Point(2, 2);
        Set<Point> oilPatches = new HashSet<>(Collections.emptyList());
        Point cleanerPosition = new Point(1, 1);
        OilSpillMap map = new OilSpillMap(grid, oilPatches, cleanerPosition);

        map.moveCleanerInDirection(Instruction.S);

        assertThat(map.getCleanerPosition()).isEqualTo(new Point(1, 0));
    }

    @Test
    void movesCleanerLeftForWestInstruction() {
        Point grid = new Point(2, 2);
        Set<Point> oilPatches = new HashSet<>(Collections.emptyList());
        Point cleanerPosition = new Point(1, 1);
        OilSpillMap map = new OilSpillMap(grid, oilPatches, cleanerPosition);

        map.moveCleanerInDirection(Instruction.W);

        assertThat(map.getCleanerPosition()).isEqualTo(new Point(0, 1));
    }

    @Test
    void movesCleanerRightForEastInstruction() {
        Point grid = new Point(2, 2);
        Set<Point> oilPatches = new HashSet<>(Collections.emptyList());
        Point cleanerPosition = new Point(1, 1);

        OilSpillMap map = new OilSpillMap(grid, oilPatches, cleanerPosition);
        map.moveCleanerInDirection(Instruction.E);

        assertThat(map.getCleanerPosition()).isEqualTo(new Point(2, 1));
    }

    @Test
    void cleansPatchThatTheCleanerStartsOn() {
        Point grid = new Point(2, 2);

        Set<Point> oilPatches = new HashSet<>();
        oilPatches.add(new Point(1, 1));

        Point cleanerPosition = new Point(1, 1);

        OilSpillMap map = new OilSpillMap(grid, oilPatches, cleanerPosition);

        assertThat(map.getOilPatches().size()).isEqualTo(0);

    }

    @Test
    void clearsOilSpillWhenCleanerGoesOntoOilPatch() {
        Point grid = new Point(2, 2);
        Set<Point> oilPatches = new HashSet<>();
        oilPatches.add(new Point(2, 2));
        oilPatches.add(new Point(1, 2));
        oilPatches.add(new Point(0, 0));

        Point cleanerPosition = new Point(1, 1);

        OilSpillMap map = new OilSpillMap(grid, oilPatches, cleanerPosition);
        assertThat(map.getOilPatches().size()).isEqualTo(3);

        map.moveCleanerInDirection(Instruction.N);
        map.moveCleanerInDirection(Instruction.E);

        assertThat(map.getCleanerPosition()).isEqualTo(new Point(2, 2));
        assertThat(map.getOilPatches().size()).isEqualTo(1);
    }

}
