package org.boldbit.illusionbackend.apigeneratorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestObject {
    private Scheme scheme;
    private Authority authority;
    private String path; // Path segments, e.g., /api/v1/users
    private String endpoint; // Path segments, e.g., users
    private String pathVariable; // Path variables, e.g., {"userId": "123"}
    private Map<String, Object> queryParameters; // Query parameters, e.g., {"filter": "active", "sort": "asc"}
    private String fragment; // Fragment identifier, e.g., "#section2"
    private HttpMethod httpMethod;
    private Map<String, Object> body; // Request body, e.g., {"name": "John Doe"}

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Authority {
        private String host; // e.g., "api.example.com"
        private String subdomain; // e.g., "api"
        private String domain; // e.g., "example"
        private String extension; // e.g., "com"
    }

    public enum Scheme {
        HTTP, HTTPS, FTP, MAILTO, FILE
    }

    public enum HttpMethod {
        GET, POST, PUT, PATCH, DELETE
    }
}

