package org.boldbit.illusionbackend.apigeneratorservice.repository;

import org.boldbit.illusionbackend.apigeneratorservice.model.DataModelsRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataModelsRegistryRepository extends MongoRepository<DataModelsRegistry, String> {
}
