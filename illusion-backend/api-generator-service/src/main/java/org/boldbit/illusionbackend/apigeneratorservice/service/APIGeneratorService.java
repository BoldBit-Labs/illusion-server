package org.boldbit.illusionbackend.apigeneratorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.ProjectServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.MethodNotAllowedException;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.NoMatchingEndpointFound;
import org.boldbit.illusionbackend.apigeneratorservice.model.*;
import org.boldbit.illusionbackend.apigeneratorservice.repository.APIDocumentsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.BigDBRepository;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class APIGeneratorService {

    private final ProjectServiceClient projectServiceClient;
    private final APIDocumentsRegistryRepository documentsRegistryRepository;
    private final BigDBRepository bigDBRepository;
    private final Utils utils;
    private final GetRequestService getRequestService;
    private final PostRequestService postRequestService;
    private final PutRequestService putRequestService;
    private final PatchRequestService patchRequestService;
    private final DeleteRequestService deleteRequestService;

    // Todo: implement schema design

    Map<String, Object> finalSchema = Map.of(
            "name", String.class,
            "details", Map.of(
                    "age", Integer.class,
                    "address", String.class,
                    "company", Map.of(
                            "name", String.class,
                            "address", Map.of(
                                    "city", String.class
                            )
                    )
            )
    );

    public ResponseEntity<?> requestHandler(String pathVariable,
                                         Map<String, Object> requestBody,
                                         Map<String, Object> allQueryParams,
                                         HttpServletRequest httpServletRequest) {

        API api = fillAPIObject(pathVariable, requestBody, allQueryParams, httpServletRequest);
        Endpoint endpoint = utils.validAPIRequest(api.getAuthority().getSubdomain(),
                api.getPath(),
                String.valueOf(api.getHttpMethod()));

        String collectionId = endpoint.collectionId();
        if (collectionId == null) {
            collectionId = utils.createCollection(api.getEndpoint(), endpoint.id());
        }

        return switch (api.getHttpMethod()) {
            case GET -> getRequestService.handleGetRequest(collectionId, api.getPathVariable());
            case POST -> postRequestService.handlePostRequest(collectionId, finalSchema, api.getBody());
            case PUT -> putRequestService.handlePutRequest(collectionId, api.getPathVariable(), finalSchema, api.getBody());
            case PATCH -> patchRequestService.handlePatchRequest(collectionId, api.getPathVariable(), finalSchema, api.getBody());
            case DELETE -> deleteRequestService.handleDeleteRequest(collectionId, api.getPathVariable());
            default -> ResponseEntity.ok(handleUnknownRequest());
        };
    }

    private Map<String, Object> handleUnknownRequest() {
        return null;
    }

    private API fillAPIObject(String pathVariable,
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
            String[] endpoints = api.getPath().split("/");
            if (endpoints.length > 1) {
                api.setEndpoint(endpoints[endpoints.length - 1]);
            }

            String[] pathVariableFinder = utils.pathVariableFinder(authority.getSubdomain(), api.getPath(), api.getHttpMethod());
            api.setPath(pathVariableFinder[0]);
            api.setPathVariable(pathVariableFinder[1]);

            api.setQueryParameters(allQueryParams);
            api.setFragment(httpServletRequest.getHeader("fragment"));
            api.setBody(requestBody);
        } catch (MethodNotAllowedException e) {
            throw new MethodNotAllowedException(e.getMessage());
        } catch (NoMatchingEndpointFound e) {
            throw new NoMatchingEndpointFound(e.getMessage());
        } catch (Exception e) {
            throw new NotFoundException(e);
        }
        return api;
    }
}
