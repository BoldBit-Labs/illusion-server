package org.boldbit.illusionbackend.apigeneratorservice.repository;

import org.boldbit.illusionbackend.apigeneratorservice.model.DataModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataModelRepository extends MongoRepository<DataModel, String> {
}
