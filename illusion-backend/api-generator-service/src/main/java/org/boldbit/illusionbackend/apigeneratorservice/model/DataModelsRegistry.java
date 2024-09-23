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
@Document("dataModelsRegistry")
public class DataModelsRegistry {

    @Id
    private String id;
    private String name;
    private List<String> dataModelIds;

}
