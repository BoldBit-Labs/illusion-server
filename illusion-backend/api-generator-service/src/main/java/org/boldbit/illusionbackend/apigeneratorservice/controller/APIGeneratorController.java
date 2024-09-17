package org.boldbit.illusionbackend.apigeneratorservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.service.APIGeneratorService;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class APIGeneratorController {
    private final APIGeneratorService apiGeneratorService;

    @RequestMapping(value = "/{endpoint}", method = RequestMethod.GET)
    public Map<String, Object> handleGetRequest(@PathVariable String endpoint,
                                                @RequestParam Map<String, String> queryParams,
                                                @RequestBody(required = false) Map<String, Object> requestBody,
                                                HttpMethod httpMethod) {

        System.out.println("Endpoint: " + endpoint);
        System.out.println("Query Params: " + queryParams);
        System.out.println("Request Body: " + requestBody);
        System.out.println("HTTP Method: " + httpMethod);

        return null;
    }

    @RequestMapping(value = "/{endpoint}/**", method = RequestMethod.POST)
    public Map<String, Object> handlePostRequest(@RequestHeader(value = "Host", required = false) String host,
                                                 @RequestHeader(value = "X-Forwarded-Host", required = false) String forwardedHost,
                                                 @PathVariable String endpoint,
                                                 @RequestParam Map<String, String> queryParams,
                                                 @RequestBody(required = false) Map<String, Object> requestBody,
                                                 HttpServletRequest request,
                                                 HttpMethod httpMethod) {

        // Use X-Forwarded-Host if available, otherwise fall back to Host
        String effectiveHost = (forwardedHost != null) ? forwardedHost : host;

        // Extract subdomain from the effectiveHost
        String subdomain = null;
        if (effectiveHost != null && effectiveHost.contains(".")) {
            String[] parts = effectiveHost.split("\\.");
            if (parts.length > 2) {
                subdomain = parts[0];  // Assuming subdomain is the first part
            }
        }

        System.out.println("Effective Host: " + effectiveHost);
        System.out.println("Subdomain: " + subdomain);

        return apiGeneratorService.postRequestHandler(effectiveHost, endpoint, queryParams, request, requestBody);
    }


}
