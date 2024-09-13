package org.boldbit.illusionbackend.projectservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "endpoints")
public class Endpoint {

    @Id
    private String id;

    @Field("project_id")
    private String projectId;

    private String name;

    private String description;

    @Field("allowed_methods")
    private Map<String, Boolean> allowedMethods;

    @Field("schema")
    private Map<String, Object> schema;

}
