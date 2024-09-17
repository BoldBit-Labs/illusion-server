package org.boldbit.illusionbackend.apigeneratorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.ProjectServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.model.Endpoint;
import org.boldbit.illusionbackend.apigeneratorservice.model.Project;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class APIGeneratorService {

    private final Utils utils;
    private final ProjectServiceClient projectServiceClient;

    public Map<String, Object> postRequestHandler(String host, String endpoint, Map<String, String> queryParams,
                                                  HttpServletRequest request, Map<String, Object> requestBody) {
        String subdomain = utils.extractSubdomain(host);
        String path = request.getRequestURI();
        String completeUrl = "http://" + host + path;

        validatePostRequest(subdomain, completeUrl);

        System.out.println("Endpoint: " + endpoint);
        System.out.println("Query Params: " + queryParams);
        System.out.println("Request Body: " + requestBody);
        System.out.println("HTTP Method: POST");

        return requestBody;
    }

    private void validatePostRequest(String projectId, String url) {
        Project project = projectServiceClient.getProjectById(projectId);

        Project.Endpoint matchingEndpoint = project.endpoints().stream()
                .filter(endpoint -> endpoint.url().equals(url))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching endpoint found for the provided URL"));

        Endpoint endpoint = projectServiceClient.getEndpointById(matchingEndpoint.id());

        if (!endpoint.allowedMethods().getOrDefault("post", false)) {
            throw new IllegalArgumentException("POST method not allowed for this endpoint");
        }

        System.out.println("Valid project: " + project);
    }
}
