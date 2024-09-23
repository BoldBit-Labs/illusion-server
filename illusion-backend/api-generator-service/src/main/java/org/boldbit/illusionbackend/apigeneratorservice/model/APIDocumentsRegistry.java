package org.boldbit.illusionbackend.apigeneratorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("api_documents_registry")
public class APIDocumentsRegistry {

    @Id
    private String id;
    private String name;
    private List<String> documentIds;

}
