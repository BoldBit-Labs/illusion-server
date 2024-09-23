package org.boldbit.illusionbackend.projectservice.controller;


import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.projectservice.model.Endpoint;
import org.boldbit.illusionbackend.projectservice.service.EndpointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/endpoints")
public class EndpointController {
    private final EndpointService endpointService;

    @PostMapping("/create-endpoint")
    private ResponseEntity<?> createEndpoint(@RequestBody Endpoint endpoint) {
        String endpointId = endpointService.createEndpoint(endpoint);
        return ResponseEntity.ok(endpointId);
    }

    @GetMapping("/endpoint/{endpointId}")
    public ResponseEntity<Endpoint> getEndpointById(@PathVariable String endpointId) {
        Endpoint endpoint = endpointService.getEndpoint(endpointId);
        return ResponseEntity.ok(endpoint);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<Endpoint>> getAllEndpoints(@PathVariable String projectId) {
        List<Endpoint> endpoints = endpointService.getAllEndpoints(projectId);
        return ResponseEntity.ok(endpoints);
    }

    @PatchMapping("/{endpointId}")
    public ResponseEntity<?> updateEndpoint(@PathVariable String endpointId, @RequestBody Map<String, Object> updates) {
        Endpoint updatedEndpoint = endpointService.updateEndpoint(endpointId, updates);
        return ResponseEntity.ok(updatedEndpoint);
    }

    @PutMapping("/{endpointId}")
    public ResponseEntity<?> updateEndpointPartially(@PathVariable String endpointId, @RequestBody Map<String, Object> updates) {
        Endpoint updatedEndpoint = endpointService.updateEndpoint(endpointId, updates);
        return ResponseEntity.ok(updatedEndpoint);
    }

    @DeleteMapping("/{endpointId}")
    public ResponseEntity<String> deleteEndpoint(@PathVariable String endpointId) {
        boolean deleted = endpointService.deleteEndpoint(endpointId);
        if (deleted) {
            return ResponseEntity.ok("Endpoint deleted successfully");
        }
        return new ResponseEntity<>("Endpoint not found", HttpStatus.NOT_FOUND);
    }
}
