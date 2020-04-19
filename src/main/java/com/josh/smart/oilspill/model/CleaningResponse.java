package com.josh.smart.oilspill.model;

import lombok.Data;

@Data
public class CleaningResponse {

    private final Integer[] finalPosition;

    private final Integer oilPatchesCleaned;

}
