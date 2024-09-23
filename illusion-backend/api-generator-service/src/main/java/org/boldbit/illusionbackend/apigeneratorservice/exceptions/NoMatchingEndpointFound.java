package org.boldbit.illusionbackend.apigeneratorservice.exceptions;

public class NoMatchingEndpointFound extends RuntimeException {
    public NoMatchingEndpointFound(String message) {
        super(message);
    }
}
