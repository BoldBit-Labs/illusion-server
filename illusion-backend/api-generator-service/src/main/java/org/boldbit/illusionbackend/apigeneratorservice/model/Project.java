package org.boldbit.illusionbackend.apigeneratorservice.model;

import java.util.List;

public record Project(
        String id,
        String name,
        String description,
        String endpointPrefix,
        String ownerId,
        List<Endpoint> endpoints
) {
    public record Endpoint(
            String id,
            String path
    ) {}
}
