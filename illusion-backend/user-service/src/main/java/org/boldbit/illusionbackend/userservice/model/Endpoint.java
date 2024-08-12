package org.boldbit.illusionbackend.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endpoint {
    private String id;
    private String path;
    private String method;
    private String body;
}
