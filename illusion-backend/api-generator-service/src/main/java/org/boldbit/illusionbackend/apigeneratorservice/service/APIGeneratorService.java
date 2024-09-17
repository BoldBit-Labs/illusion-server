package org.boldbit.illusionbackend.apigeneratorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.ProjectServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.model.API;
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

    public Map<String, Object> requestHandler(String pathVariable,
                                              Map<String, Object> requestBody,
                                              Map<String, Object> allQueryParams,
                                              HttpServletRequest httpServletRequest) {

        API api = fillAPI(pathVariable, requestBody, allQueryParams, httpServletRequest);

        validAPIRequest(api.getAuthority().getSubdomain(), api.getPath(), String.valueOf(api.getHttpMethod()));

        return null;
    }

    private API fillAPI(String pathVariable,
                        Map<String, Object> requestBody,
                        Map<String, Object> allQueryParams,
                        HttpServletRequest httpServletRequest) {
        API api = new API();
        try {
            api.setHttpMethod(API.HttpMethod.valueOf(httpServletRequest.getMethod()));
            api.setScheme(API.Scheme.valueOf(httpServletRequest.getScheme().toUpperCase()));

            String host = httpServletRequest.getHeader("x-forwarded-host");
            API.Authority authority = new API.Authority();
            authority.setSubdomain(host.split("\\.")[0]);
            authority.setDomain(host.split("\\.")[1]);
            authority.setExtension(host.split("\\.")[2].split(":")[0]);
            authority.setPort(Integer.parseInt(host.split("\\.")[2].split(":")[1]));
            authority.setHost(host);
            api.setAuthority(authority);

            api.setPath(httpServletRequest.getRequestURI());
            api.setBody(requestBody);
            api.setPathVariable(pathVariable);
            api.setQueryParameters(allQueryParams);
            api.setFragment(httpServletRequest.getHeader("fragment"));
        } catch (Exception e) {
            throw new NotFoundException(e);
        }
        return api;
    }

    private void validAPIRequest(String projectId, String path, String httpMethod) {
        Project project = projectServiceClient.getProjectById(projectId);

        Project.Endpoint matchingEndpoint = project.endpoints().stream()
                .filter(endpoint -> endpoint.url().equals(path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching endpoint found for the provided URL"));

        Endpoint endpoint = projectServiceClient.getEndpointById(matchingEndpoint.id());

        // fixme: fix method case
        if (!endpoint.allowedMethods().getOrDefault(httpMethod.toLowerCase(), false)) {
            throw new IllegalArgumentException("Http method not allowed for this endpoint");
        }

        System.out.println("Valid API request");
    }

    private void validateSchema(){

    }
}
