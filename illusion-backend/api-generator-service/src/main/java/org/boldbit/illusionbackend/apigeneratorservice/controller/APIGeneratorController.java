package org.boldbit.illusionbackend.apigeneratorservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.service.APIGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/", "/{endpoint}/**"})
@RequiredArgsConstructor
public class APIGeneratorController {

    private final APIGeneratorService apiGeneratorService;

    @GetMapping
    public ResponseEntity<?> handleGetRequest(@RequestParam Map<String, Object> allQueryParams,
                                              HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, null, allQueryParams, httpServletRequest);
    }

    @PostMapping
    public ResponseEntity<?> handlePostRequest(@RequestBody Map<String, Object> requestBody,
                                               @RequestParam Map<String, Object> allQueryParams,
                                               HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, requestBody, allQueryParams, httpServletRequest);
    }

    @PutMapping
    public ResponseEntity<?> handlePutRequest(@RequestBody Map<String, Object> requestBody,
                                              @RequestParam Map<String, Object> allQueryParams,
                                              HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, requestBody, allQueryParams, httpServletRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> handleDeleteRequest(@RequestParam Map<String, Object> allQueryParams,
                                                 HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, null, allQueryParams, httpServletRequest);
    }

    @PatchMapping
    public ResponseEntity<?> handlePatchRequest(@RequestBody Map<String, Object> requestBody,
                                                @RequestParam Map<String, Object> allQueryParams,
                                                HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, requestBody, allQueryParams, httpServletRequest);
    }
}

