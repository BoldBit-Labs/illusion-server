package org.boldbit.illusionbackend.projectservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.projectservice.clients.UserClient;
import org.boldbit.illusionbackend.projectservice.exceptions.EndpointNotFoundException;
import org.boldbit.illusionbackend.projectservice.exceptions.ProjectNotFoundException;
import org.boldbit.illusionbackend.projectservice.model.Endpoint;
import org.boldbit.illusionbackend.projectservice.model.Project;
import org.boldbit.illusionbackend.projectservice.repository.EndpointRepository;
import org.boldbit.illusionbackend.projectservice.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EndpointRepository endpointRepository;
    private final UserClient userClient;

    public String createProject(Project project) {
        Optional<Project> existingProject = projectRepository.findByOwnerIdAndName(project.getOwnerId(), project.getName());
        if (existingProject.isPresent()) {
            throw new IllegalArgumentException("Project with the same name already exists");
        }
        String projectId = projectRepository.save(project).getId();
        Map<String, String> projectProperties = new HashMap<>();
        projectProperties.put("projectId", projectId);
        projectProperties.put("projectName", project.getName());

        userClient.updateUser(project.getOwnerId(), Collections.singletonMap("projects", projectProperties));
        return projectId;
    }

    public Project getProject(String projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));
    }

    public List<Project> getAllProjects(String ownerId) {
        return projectRepository.findAllByOwnerId(ownerId);
    }

    public boolean deleteProject(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        projectRepository.delete(project);
        if (project.getEndpoints() != null) {
            deleteAllEndpoints(project.getEndpoints());
        }
        userClient.updateUser(project.getOwnerId(), Collections.singletonMap("projects", projectId));

        return true;
    }

    public void deleteAllEndpoints(List<Project.Endpoint> endpoints) {
        List<String> endpointIds = new ArrayList<>(endpoints.stream()
                .map(Project.Endpoint::getId)
                .toList());

        List<Endpoint> foundEndpoints = endpointRepository.findAllById(endpointIds);

        if (foundEndpoints.size() != endpointIds.size()) {
            List<String> foundIds = foundEndpoints.stream().map(Endpoint::getId).toList();
            endpointIds.removeAll(foundIds);
            log.warn("Endpoints not found: {}", endpointIds);
            throw new EndpointNotFoundException("Some endpoints were not found: " + endpointIds);
        }

        endpointRepository.deleteAll(foundEndpoints);
        log.info("All endpoints deleted successfully");
    }

    public Project updateProject(String projectId, Map<String, Object> updates) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        updates.forEach((field, value) -> {
            switch (field) {
                case "name":
                    project.setName((String) value);
                    break;
                case "description":
                    project.setDescription((String) value);
                    break;
                case "endpointPrefix":
                    project.setEndpointPrefix((String) value);
                    break;
                case "endpoints":
                    if (value instanceof Map) {
                        updateEndpoints(project, (Map<String, String>) value);
                    } else if (value instanceof String) {
                        removeEndpoint(project, (String) value);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown update field: " + field);
            }
        });

        projectRepository.save(project);
        log.info("Project with ID {} updated successfully", projectId);
        return project;
    }

    private void updateEndpoints(Project project, Map<String, String> updates) {
        List<Project.Endpoint> endpoints = Optional.ofNullable(project.getEndpoints()).orElse(new ArrayList<>());

        boolean endpointExists = endpoints.stream()
                .anyMatch(endpoint -> endpoint.getPath() != null && endpoint.getPath().equals(updates.get("path")));

        if (endpointExists) {
            log.warn("Endpoint with Path '{}' already exists", updates.get("path"));
            // todo: update path in project also
            throw new IllegalArgumentException("Endpoint already exists");
        }

        endpoints.add(new Project.Endpoint(updates.get("id"), updates.get("path")));
        project.setEndpoints(endpoints);
        projectRepository.save(project);
        log.info("Endpoint added successfully");
    }

    private void removeEndpoint(Project project, String endpointId) {
        List<Project.Endpoint> endpoints = Optional.ofNullable(project.getEndpoints()).orElse(new ArrayList<>());

        List<Project.Endpoint> updatedEndpoints = endpoints.stream()
                .filter(endpoint -> !endpoint.getId().equals(endpointId))
                .toList();

        if (updatedEndpoints.size() == endpoints.size()) {
            log.warn("Endpoint with ID '{}' not found", endpointId);
            throw new EndpointNotFoundException("Endpoint not found");
        }

        project.setEndpoints(updatedEndpoints);
        log.info("Endpoint with ID '{}' removed successfully", endpointId);
    }
}
