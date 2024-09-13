package org.boldbit.illusionbackend.projectservice.controller;


import org.boldbit.illusionbackend.projectservice.model.Endpoint;
import org.boldbit.illusionbackend.projectservice.service.EndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/endpoints")
public class EndpointController {
    private static final Logger log = LoggerFactory.getLogger(EndpointController.class);
    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @PostMapping("/create-endpoint")
    private ResponseEntity<?> createEndpoint(@RequestBody Endpoint endpoint) {
        try {
            String endpointId = endpointService.createEndpoint(endpoint);
            if (endpointId != null) {
                return ResponseEntity.ok().body(endpointId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

}
