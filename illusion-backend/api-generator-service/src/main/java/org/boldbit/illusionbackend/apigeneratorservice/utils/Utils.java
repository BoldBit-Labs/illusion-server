package org.boldbit.illusionbackend.apigeneratorservice.utils;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.ProjectServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.MethodNotAllowedException;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.NoMatchingEndpointFound;
import org.boldbit.illusionbackend.apigeneratorservice.model.API;
import org.boldbit.illusionbackend.apigeneratorservice.model.DataModelsRegistry;
import org.boldbit.illusionbackend.apigeneratorservice.model.Endpoint;
import org.boldbit.illusionbackend.apigeneratorservice.model.Project;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelsRegistryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
public class Utils {

    private final ProjectServiceClient projectServiceClient;
    private final DataModelsRegistryRepository registryRepository;

    public void validateSchema(Map<String, Object> schema, Map<String, Object> body) {
        body.forEach((field, value) -> {
            if (schema.containsKey(field)) {
                Object expectedType = schema.get(field);

                if (expectedType instanceof Class<?>) {
                    if (!((Class<?>) expectedType).isInstance(value)) {
                        throw new RuntimeException("Invalid type for field '" + field + "'. Expected: "
                                + ((Class<?>) expectedType).getSimpleName() + ", but got: " + value.getClass().getSimpleName());
                    }
                } else if (expectedType instanceof Map) {
                    if (value instanceof Map) {
                        validateSchema((Map<String, Object>) expectedType, (Map<String, Object>) value);
                    } else {
                        throw new RuntimeException("Expected a nested object for field '" + field + "'");
                    }
                } else {
                    throw new RuntimeException("Unknown schema type for field '" + field + "'");
                }
            } else {
                throw new RuntimeException("Unknown field '" + field + "'");
            }
        });
    }

    public String createCollection(String endpoint, String endpointId) {
        DataModelsRegistry registry = new DataModelsRegistry();
        registry.setName(endpoint);
        registry.setDataModelIds(new ArrayList<>());
        String collectionId = registryRepository.save(registry).getId();

        projectServiceClient.updateEndpointPartially(endpointId, Collections.singletonMap("collectionId", collectionId));
        return collectionId;
    }

    public String[] pathVariableFinder(String projectId, String path, API.HttpMethod httpMethod) {
        String[] data = new String[2];
        String[] paths = paths(path);
        data[1] = paths[3];
        if (httpMethod.equals(API.HttpMethod.GET)) {
            try {
                validAPIRequest(projectId, paths[0], httpMethod.toString());
                data[0] = paths[0];
                data[1] = null;
                return data;
            } catch (MethodNotAllowedException ex) {
                throw new MethodNotAllowedException(ex.getMessage());
            } catch (NoMatchingEndpointFound e) {
                try {
                    validAPIRequest(projectId, paths[1], httpMethod.toString());
                    data[0] = paths[1];
                    return data;
                } catch (MethodNotAllowedException ex) {
                    throw new MethodNotAllowedException(ex.getMessage());
                } catch (NoMatchingEndpointFound ex) {
                    validAPIRequest(projectId, paths[2], httpMethod.toString());
                    data[0] = paths[2];
                    return data;
                }
            }
        } else if (httpMethod.equals(API.HttpMethod.POST)) {
            validAPIRequest(projectId, paths[0], httpMethod.toString());
            data[0] = paths[0];
            data[1] = null;
            return data;
        } else if (httpMethod.equals(API.HttpMethod.PUT) ||
                httpMethod.equals(API.HttpMethod.PATCH) ||
                httpMethod.equals(API.HttpMethod.DELETE)) {
            try {
                validAPIRequest(projectId, paths[1], httpMethod.toString());
                data[0] = paths[1];
                return data;
            } catch (MethodNotAllowedException e) {
                throw new MethodNotAllowedException(e.getMessage());
            } catch (NoMatchingEndpointFound e ) {
                validAPIRequest(projectId, paths[2], httpMethod.toString());
                data[0] = paths[2];
                return data;
            }
        } else {
            throw new RuntimeException("Unsupported HTTP method: " + httpMethod);
        }
    }

    private String[] paths(String path) {
        String[] finalArray = new String[4];
        finalArray[0] = path;

        int lastSlash = path.lastIndexOf("/");
        if (lastSlash != path.length() - 1) {
            finalArray[1] = path.substring(0, lastSlash + 1);
            finalArray[2] = path.substring(0, lastSlash);
            finalArray[3] = path.substring(lastSlash + 1);
        } else {
            finalArray[1] = path.substring(0, lastSlash);
        }

        return finalArray;
    }

    public Endpoint validAPIRequest(String projectId, String path, String httpMethod) {
        Project project = projectServiceClient.getProjectById(projectId);

        Project.Endpoint matchingEndpoint = project.endpoints().stream()
                .filter(endpoint -> endpoint.url().equals(path))
                .findFirst()
                .orElseThrow(() -> new NoMatchingEndpointFound("No matching endpoint found for the provided URL"));

        Endpoint endpoint = projectServiceClient.getEndpointById(matchingEndpoint.id());

        // fixme: fix method case
        if (!endpoint.allowedMethods().getOrDefault(httpMethod.toLowerCase(), false)) {
            throw new MethodNotAllowedException(httpMethod + " Http method not allowed for this endpoint");
        }

        return endpoint;
    }

}
