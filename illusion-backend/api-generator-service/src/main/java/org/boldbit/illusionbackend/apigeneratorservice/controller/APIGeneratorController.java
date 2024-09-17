package org.boldbit.illusionbackend.apigeneratorservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.service.APIGeneratorService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/{endpoint}/**")
@RequiredArgsConstructor
public class APIGeneratorController {

    private final APIGeneratorService apiGeneratorService;

    @GetMapping
    public Map<String, Object> handleGetRequest(@RequestParam Map<String, Object> allQueryParams,
                                                HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, null, allQueryParams, httpServletRequest);
    }

    @PostMapping
    public Map<String, Object> handlePostRequest(@RequestBody Map<String, Object> requestBody,
                                                 @RequestParam Map<String, Object> allQueryParams,
                                                 HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, requestBody, allQueryParams, httpServletRequest);
    }

    @PutMapping
    public Map<String, Object> handlePutRequest(@RequestBody Map<String, Object> requestBody,
                                                @RequestParam Map<String, Object> allQueryParams,
                                                HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, requestBody, allQueryParams, httpServletRequest);
    }

    @DeleteMapping
    public Map<String, Object> handleDeleteRequest(@RequestParam Map<String, Object> allQueryParams,
                                                   HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, null, allQueryParams, httpServletRequest);
    }

    @PatchMapping
    public Map<String, Object> handlePatchRequest(@RequestBody Map<String, Object> requestBody,
                                                  @RequestParam Map<String, Object> allQueryParams,
                                                  HttpServletRequest httpServletRequest) {
        return apiGeneratorService.requestHandler(null, requestBody, allQueryParams, httpServletRequest);
    }
}

