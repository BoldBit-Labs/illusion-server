package org.boldbit.illusionbackend.projectservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "apis")
public class API {
    @Id
    private String id;

    @Field("project_id")
    private String projectId;

    private String endpoint;
    private String method;

    @Field("request_schema")
    private Map<String, Object> requestSchema;

    @Field("response_schema")
    private Map<String, Object> responseSchema;
}
