package org.boldbit.illusionbackend.apigeneratorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface APIGeneratorService {
    Map<String, Object> postRequestHandler(String host, String endpoint, Map<String, String> queryParams, HttpServletRequest request, Map<String, Object> requestBody);
}
