package org.boldbit.illusionbackend.projectservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class Project {

    @Id
    private String id;
    private String name;
    private String description;
    private String endpointPrefix;

    @Field("owner_id")
    private String ownerId;

    private List<Endpoint> endpoints;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Endpoint {
        private String id;
        private String path;
    }
}
