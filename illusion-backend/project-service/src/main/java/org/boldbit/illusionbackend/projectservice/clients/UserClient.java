package org.boldbit.illusionbackend.projectservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8890")
public interface UserClient {
    @PostMapping("/api/user/projects/{userId}")
    String addProjectId(@PathVariable String userId, @RequestBody String projectId);
}
