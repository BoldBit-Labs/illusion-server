package org.boldbit.illusionbackend.projectservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user-service", url = "http://localhost:8890")
public interface UserClient {

    @PutMapping("/api/user/{userId}")
    String updateUser(@PathVariable String userId, @RequestBody Map<String, Object> updates);

}
