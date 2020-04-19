package com.josh.smart.oilspill.model;

import lombok.Getter;

@Getter
public enum Instruction {

    N(0, 1),
    E(1, 0),
    S(0, -1),
    W(-1, 0);

    int xMovement;
    int yMovement;

    Instruction(int xMovement, int yMovement) {
        this.xMovement = xMovement;
        this.yMovement = yMovement;
    }

}
