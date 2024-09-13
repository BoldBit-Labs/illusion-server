package org.boldbit.illusionbackend.apigeneratorservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:9000")
public interface UserServiceClient {
    @PostMapping("/api/user/projects/{userId}")
    String addProjectId(@PathVariable String userId, @RequestBody String projectId);
}
