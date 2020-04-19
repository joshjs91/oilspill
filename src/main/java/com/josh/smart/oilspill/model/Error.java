package com.josh.smart.oilspill.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {

    private String message;

    private String code;

}
