package org.boldbit.illusionbackend.apigeneratorservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "project-service", url = "http://localhost:9001")
public interface ProjectServiceClient {
//    @PostMapping("/api/user/projects/{userId}")
//    String addProjectId(@PathVariable String userId, @RequestBody String projectId);
}
