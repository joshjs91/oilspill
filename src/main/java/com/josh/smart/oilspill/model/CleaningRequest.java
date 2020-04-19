package com.josh.smart.oilspill.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CleaningRequest {

    @NotNull
    @Size(min = 2, max = 2, message = "Area size must be an array of size 2")
    private Integer[] areaSize;

    @NotNull
    @Size(min = 2, max = 2, message = "Starting position must be an array of size 2")
    private Integer[] startingPosition;

    @NotNull
    private Set<Integer[]> oilPatches;

    @NotNull
    private String navigationInstructions;

}
