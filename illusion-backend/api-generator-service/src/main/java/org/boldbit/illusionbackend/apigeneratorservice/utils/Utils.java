package org.boldbit.illusionbackend.apigeneratorservice.utils;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.clients.ProjectServiceClient;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.BodyMismatchException;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.MethodNotAllowedException;
import org.boldbit.illusionbackend.apigeneratorservice.exceptions.NoMatchingEndpointFound;
import org.boldbit.illusionbackend.apigeneratorservice.model.API;
import org.boldbit.illusionbackend.apigeneratorservice.model.DataModelsRegistry;
import org.boldbit.illusionbackend.apigeneratorservice.model.Endpoint;
import org.boldbit.illusionbackend.apigeneratorservice.model.Project;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelsRegistryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
public class Utils {

    private final ProjectServiceClient projectServiceClient;
    private final DataModelsRegistryRepository registryRepository;

    public void validateSchema(Map<String, Object> schema, Map<String, Object> body) {
        Map<String, Object> bodySchema = mapJsonSchemaToJava(body);

        if (!validate(schema, bodySchema)) {
            throw new BodyMismatchException("Body mismatch");
        }
    }

    public boolean validate(Map<String, Object> schema, Map<String, Object> body) {
        for (Map.Entry<String, Object> entry : body.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            if (!schema.containsKey(field)) {
                throw new BodyMismatchException("Body mismatch");
            }

            Object expectedSchemaValue = schema.get(field);

            if (expectedSchemaValue instanceof Map && value instanceof Map) {
                if (!validate((Map<String, Object>) expectedSchemaValue, (Map<String, Object>) value)) {
                    return false;
                }
            } else if (!expectedSchemaValue.equals(value)) {
                throw new BodyMismatchException("Body mismatch");
            }
        }
        return true;
    }

    public static Map<String, Object> mapJsonSchemaToJava(Map<String, Object> schema) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : schema.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                resultMap.put(key, mapJsonSchemaToJava((Map<String, Object>) value));
            } else if (value instanceof List) {
                resultMap.put(key, List.class.getName());
            } else {
                resultMap.put(key, mapToJavaClassName(value.getClass().getName()));
            }
        }

        return resultMap;
    }

    private static String mapToJavaClassName(String type) {
        return switch (type) {
            case "java.lang.String" -> String.class.getName();
            case "java.lang.Integer", "java.lang.Number" -> Number.class.getName();
            case "java.lang.Float" -> Float.class.getName();
            case "java.lang.Boolean" -> Boolean.class.getName();
            case "java.util.Date" -> Date.class.getName();
            case "java.util.List" -> List.class.getName();
            case "java.util.Map" -> Map.class.getName();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
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
