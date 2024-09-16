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
                .findByProjectIdAndUrl(endpoint.getProjectId(), endpoint.getUrl());

        if (existingEndpoint.isPresent()) {
            throw new RuntimeException("Endpoint with the same URL already exists");
        }
        String endpointId = endpointRepository.save(endpoint).getId();

        Map<String, Object> endpointProperties = new HashMap<>();
        endpointProperties.put("id", endpointId);
        endpointProperties.put("url", endpoint.getUrl());
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
                case "url":
                    endpoint.setUrl((String) value);
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
}
