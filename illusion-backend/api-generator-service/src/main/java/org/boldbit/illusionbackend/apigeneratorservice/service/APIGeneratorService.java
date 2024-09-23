package org.boldbit.illusionbackend.apigeneratorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.ProjectServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.MethodNotAllowedException;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.NoMatchingEndpointFound;
import org.boldbit.illusionbackend.apigeneratorservice.model.*;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelRepository;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class APIGeneratorService {

    private final ProjectServiceClient projectServiceClient;
    private final DataModelsRegistryRepository registryRepository;
    private final DataModelRepository bigDBRepository;
    private final Utils utils;
    private final GetRequestService getRequestService;
    private final PostRequestService postRequestService;
    private final PutRequestService putRequestService;
    private final PatchRequestService patchRequestService;
    private final DeleteRequestService deleteRequestService;

    public ResponseEntity<?> requestHandler(String pathVariable,
                                         Map<String, Object> requestBody,
                                         Map<String, Object> allQueryParams,
                                         HttpServletRequest httpServletRequest) {

        RequestObject requestObject = fillRequestObject(pathVariable, requestBody, allQueryParams, httpServletRequest);
        Endpoint endpoint = utils.validAPIRequest(requestObject.getAuthority().getSubdomain(),
                requestObject.getPath(),
                String.valueOf(requestObject.getHttpMethod()));

        Map<String, Object> schema = endpoint.schema();

        String collectionId = endpoint.collectionId();
        if (collectionId == null) {
            collectionId = utils.createCollection(requestObject.getEndpoint(), endpoint.id());
        }

        return switch (requestObject.getHttpMethod()) {
            case GET -> getRequestService.handleGetRequest(collectionId, requestObject.getPathVariable());
            case POST -> postRequestService.handlePostRequest(collectionId, schema, requestObject.getBody());
            case PUT -> putRequestService.handlePutRequest(collectionId, requestObject.getPathVariable(), schema, requestObject.getBody());
            case PATCH -> patchRequestService.handlePatchRequest(collectionId, requestObject.getPathVariable(), schema, requestObject.getBody());
            case DELETE -> deleteRequestService.handleDeleteRequest(collectionId, requestObject.getPathVariable());
            default -> ResponseEntity.ok(handleUnknownRequest());
        };
    }

    private Map<String, Object> handleUnknownRequest() {
        return null;
    }

    private RequestObject fillRequestObject(String pathVariable,
                                        Map<String, Object> requestBody,
                                        Map<String, Object> allQueryParams,
                                        HttpServletRequest httpServletRequest) {
        RequestObject requestObject = new RequestObject();
        try {
            requestObject.setHttpMethod(RequestObject.HttpMethod.valueOf(httpServletRequest.getMethod()));
            requestObject.setScheme(RequestObject.Scheme.valueOf(httpServletRequest.getScheme().toUpperCase()));

            String host = httpServletRequest.getHeader("x-forwarded-host");
            RequestObject.Authority authority = new RequestObject.Authority();
            authority.setSubdomain(host.split("\\.")[0]);
            authority.setDomain(host.split("\\.")[1]);
            authority.setExtension(host.split("\\.")[2].split(":")[0]);
            authority.setPort(Integer.parseInt(host.split("\\.")[2].split(":")[1]));
            authority.setHost(host);
            requestObject.setAuthority(authority);

            requestObject.setPath(httpServletRequest.getRequestURI());
            String[] endpoints = requestObject.getPath().split("/");
            if (endpoints.length > 1) {
                requestObject.setEndpoint(endpoints[endpoints.length - 1]);
            }

            String[] pathVariableFinder = utils.pathVariableFinder(authority.getSubdomain(), requestObject.getPath(), requestObject.getHttpMethod());
            requestObject.setPath(pathVariableFinder[0]);
            requestObject.setPathVariable(pathVariableFinder[1]);

            requestObject.setQueryParameters(allQueryParams);
            requestObject.setFragment(httpServletRequest.getHeader("fragment"));
            requestObject.setBody(requestBody);
        } catch (MethodNotAllowedException e) {
            throw new MethodNotAllowedException(e.getMessage());
        } catch (NoMatchingEndpointFound e) {
            throw new NoMatchingEndpointFound(e.getMessage());
        } catch (Exception e) {
            throw new NotFoundException(e);
        }
        return requestObject;
    }
}
