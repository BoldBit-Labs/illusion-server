package org.boldbit.illusionbackend.projectservice.service;

import org.boldbit.illusionbackend.projectservice.model.Endpoint;
import org.boldbit.illusionbackend.projectservice.repository.EndpointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EndpointServiceImpl implements EndpointService {
    private final EndpointRepository endpointRepository;
    private final ProjectService projectService;

    public EndpointServiceImpl(EndpointRepository endpointRepository, ProjectService projectService) {
        this.endpointRepository = endpointRepository;
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public String createEndpoint(Endpoint endpoint) {
        Optional<Endpoint> existingEndpoint = findEndpointByProjectIdAndEndpointName(endpoint.getProjectId(), endpoint.getName());
        if (existingEndpoint.isPresent()) {
            throw new RuntimeException("Endpoint with the same name already exists");
        }
        String endpointId = endpointRepository.save(endpoint).getId();
        return projectService.addEndpointId(endpoint.getProjectId(), endpointId);
    }

    @Override
    public Optional<Endpoint> findEndpointByProjectIdAndEndpointName(String projectId, String endpointName) {
        return endpointRepository.findByProjectIdAndName(projectId, endpointName);
    }

    @Override
    public Endpoint getEndpoint(String endpointId) {
        return endpointRepository.findById(endpointId).orElse(null);
    }

    @Override
    public List<Endpoint> getAllEndpoints(String projectId) {
        return endpointRepository.findAllByProjectId(projectId);
    }
}
