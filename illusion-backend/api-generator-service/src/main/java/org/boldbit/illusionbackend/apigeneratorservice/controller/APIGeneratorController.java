package org.boldbit.illusionbackend.apigeneratorservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.apigeneratorservice.service.APIGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/", "/{endpoint}/**"})
@RequiredArgsConstructor
@Slf4j
public class APIGeneratorController {

    private final APIGeneratorService apiGeneratorService;
    private static final Logger logger = LoggerFactory.getLogger(APIGeneratorController.class);

    @GetMapping
    public ResponseEntity<?> handleGetRequest(@RequestParam Map<String, Object> allQueryParams,
                                              HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, null, allQueryParams, httpServletRequest);
    }

    @PostMapping
    public ResponseEntity<?> handlePostRequest(@RequestBody Map<String, Object> requestBody,
                                               @RequestParam Map<String, Object> allQueryParams,
                                               HttpServletRequest httpServletRequest) {
        logger.info("Received POST request");
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

