package org.boldbit.illusionbackend.apigeneratorservice.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public String extractSubdomain(String host) {
        if (host.contains(".illusion.com")) {
            String[] parts = host.split("\\.");
            if (parts.length > 2) {
                return parts[0];
            }
        }
        return "default";
    }
}
