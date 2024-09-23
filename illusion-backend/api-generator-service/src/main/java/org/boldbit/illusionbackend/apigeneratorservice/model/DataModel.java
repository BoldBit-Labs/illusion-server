package org.boldbit.illusionbackend.apigeneratorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("dataContainer")
public class DataModel {
    @Id
    private String id;
    private Map<String, Object> jsonObject;
}
