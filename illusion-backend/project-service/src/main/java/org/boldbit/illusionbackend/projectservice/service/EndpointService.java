package org.boldbit.illusionbackend.projectservice.service;

import org.boldbit.illusionbackend.projectservice.model.Endpoint;
import org.boldbit.illusionbackend.projectservice.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EndpointService {
    String createEndpoint(Endpoint endpoint);
    Optional<Endpoint> findEndpointByProjectIdAndEndpointName(String projectId, String endpointName);
    Endpoint getEndpoint(String endpointId);
    List<Endpoint> getAllEndpoints(String projectId);
}
