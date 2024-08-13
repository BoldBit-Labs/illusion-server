package org.boldbit.illusionbackend.projectservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String id;
    private String name;
    private String description;
    private List<Endpoint> endpoints;
}
