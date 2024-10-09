package org.boldbit.illusionbackend.projectservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.projectservice.exceptions.EndpointNotFoundException;
import org.boldbit.illusionbackend.projectservice.model.Endpoint;
import org.boldbit.illusionbackend.projectservice.repository.EndpointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EndpointService {
    private final EndpointRepository endpointRepository;
    private final ProjectService projectService;

    public String createEndpoint(Endpoint endpoint) {
        Optional<Endpoint> existingEndpoint = endpointRepository
                .findByProjectIdAndPath(endpoint.getProjectId(), endpoint.getPath());

        if (existingEndpoint.isPresent()) {
            throw new RuntimeException("Endpoint with the same Path already exists");
        }

        endpoint.setSchema(mapJsonSchemaToJava(endpoint.getSchema()));
        String endpointId = endpointRepository.save(endpoint).getId();

        Map<String, Object> endpointProperties = new HashMap<>();
        endpointProperties.put("id", endpointId);
        endpointProperties.put("path", endpoint.getPath());
        projectService.updateProject(endpoint.getProjectId(), Collections.singletonMap("endpoints", endpointProperties));

        return endpointId;
    }

    public Endpoint getEndpoint(String endpointId) {
        return endpointRepository.findById(endpointId)
                .orElseThrow(() -> new EndpointNotFoundException("Endpoint with id " + endpointId + " not found"));
    }

    public List<Endpoint> getAllEndpoints(String projectId) {
        return endpointRepository.findAllByProjectId(projectId);
    }

    public boolean deleteEndpoint(String endpointId) {
        Endpoint endpoint = endpointRepository.findById(endpointId)
                .orElseThrow(() -> new EndpointNotFoundException("Endpoint not found"));

        endpointRepository.delete(endpoint);
        projectService.updateProject(endpoint.getProjectId(), Collections.singletonMap("endpoints", endpointId));

        log.info("Endpoint deleted with ID: {}", endpointId);
        return true;
    }

    public Endpoint updateEndpoint(String endpointId, Map<String, Object> updates) {
        Endpoint endpoint = endpointRepository.findById(endpointId)
                .orElseThrow(() -> new EndpointNotFoundException("Endpoint not found"));

        updates.forEach((field, value) -> {
            switch (field) {
                case "path":
                    endpoint.setPath((String) value);
                    break;
                case "collectionId":
                    endpoint.setCollectionId((String) value);
                    break;
                case "description":
                    endpoint.setDescription((String) value);
                    break;
                case "allowedMethods":
                    endpoint.setAllowedMethods((Map<String, Boolean>) value);
                    break;
                case "schema":
                    endpoint.setSchema((Map<String, Object>) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown update field: " + field);
            }
        });

        endpointRepository.save(endpoint);
        log.info("Endpoint with ID {} updated successfully", endpointId);
        return endpoint;
    }

    public static Map<String, Object> mapJsonSchemaToJava(Map<String, Object> schema) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : schema.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                resultMap.put(key, mapJsonSchemaToJava((Map<String, Object>) value));
            } else if (value instanceof String) {
                resultMap.put(key, mapToJavaClassName((String) value));
            }
        }
        return resultMap;
    }

    private static String mapToJavaClassName(String type) {
        return switch (type) {
            case "String" -> String.class.getName();
            case "Number" -> Number.class.getName();
            case "Float" -> Float.class.getName();
            case "Boolean" -> Boolean.class.getName();
            case "Date" -> Date.class.getName();
            case "Array" -> List.class.getName();
            case "Object" -> Map.class.getName();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
