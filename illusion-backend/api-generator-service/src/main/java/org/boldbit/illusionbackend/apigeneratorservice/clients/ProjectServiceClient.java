package org.boldbit.illusionbackend.apigeneratorservice.clients;

import org.boldbit.illusionbackend.apigeneratorservice.model.Endpoint;
import org.boldbit.illusionbackend.apigeneratorservice.model.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "project-service", url = "http://localhost:8891")
public interface ProjectServiceClient {

    @GetMapping("/api/projects/project/{projectId}")
    Project getProjectById(@PathVariable String projectId);

    @GetMapping("/api/endpoints/endpoint/{endpointId}")
    Endpoint getEndpointById(@PathVariable String endpointId);
}
