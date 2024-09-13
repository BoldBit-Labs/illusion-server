package org.boldbit.illusionbackend.apigeneratorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class APIGeneratorServiceImpl implements APIGeneratorService {

    @Autowired
    Utils utils;

    @Override
    public Map<String, Object> postRequestHandler(String host, String endpoint, Map<String, String> queryParams, HttpServletRequest request, Map<String, Object> requestBody) {
        String subdomain = utils.extractSubdomain(host);
        String path = request.getRequestURI();
        System.out.println("Endpoint: " + endpoint);
        System.out.println("Query Params: " + queryParams);
        System.out.println("Request Body: " + requestBody);
        System.out.println("HTTP Method: POST");
        return requestBody;
    }
}
