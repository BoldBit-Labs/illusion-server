package org.boldbit.illusionbackend.apigeneratorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.UserServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class APIGeneratorService {

    private final Utils utils;
    private final UserServiceClient userServiceClient;

    public Map<String, Object> postRequestHandler(String host, String endpoint, Map<String, String> queryParams, HttpServletRequest request, Map<String, Object> requestBody) {
        String subdomain = utils.extractSubdomain(host);
        String path = request.getRequestURI();
        Object obj = userServiceClient.addProjectId("66d7f386febf2e6647bb0bff", "66d82fd4c070f14e7f57a824");
        System.out.println("Endpoint: " + endpoint);
        System.out.println("Query Params: " + queryParams);
        System.out.println("Request Body: " + requestBody);
        System.out.println("HTTP Method: POST");
        return requestBody;
    }
}
