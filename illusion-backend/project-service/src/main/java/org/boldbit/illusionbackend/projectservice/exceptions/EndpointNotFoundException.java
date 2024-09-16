package org.boldbit.illusionbackend.projectservice.exceptions;

public class EndpointNotFoundException extends RuntimeException {
    public EndpointNotFoundException(String message) {
        super(message);
    }
}
