package com.josh.smart.oilspill.controller;

import com.josh.smart.oilspill.model.CleaningRequest;
import com.josh.smart.oilspill.model.CleaningResponse;
import com.josh.smart.oilspill.service.OilCleanupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class OilCleanupController {

    private final OilCleanupService oilCleanupService;

    @PostMapping("/cleanup")
    public ResponseEntity<CleaningResponse> cleanUp(@Valid @RequestBody CleaningRequest cleaningRequestDto) {
        return ResponseEntity.ok(oilCleanupService.cleanup(cleaningRequestDto));
    }

}
