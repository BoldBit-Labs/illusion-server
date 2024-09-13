package org.boldbit.illusionbackend.apigeneratorservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.boldbit.illusionbackend.apigeneratorservice.service.APIGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class APIGeneratorController {

    private final APIGeneratorService apiGeneratorService;

    public APIGeneratorController(APIGeneratorService apiGeneratorService) {
        this.apiGeneratorService = apiGeneratorService;
    }


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
    public Map<String, Object> handlePostRequest(@RequestHeader("Host") String host,
                                                 @PathVariable String endpoint,
                                                 @RequestParam Map<String, String> queryParams,
                                                 @RequestBody(required = false) Map<String, Object> requestBody,
                                                 HttpServletRequest request,
                                                 HttpMethod httpMethod) {

        return apiGeneratorService.postRequestHandler(host, endpoint, queryParams, request, requestBody);
    }

}
