package org.boldbit.illusionbackend.apigeneratorservice.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "project-service", url = "http://localhost:8891")
public interface ProjectServiceClient {
//    @PostMapping("/api/user/projects/{userId}")
//    String addProjectId(@PathVariable String userId, @RequestBody String projectId);
}
