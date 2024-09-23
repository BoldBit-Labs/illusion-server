package org.boldbit.illusionbackend.apigeneratorservice.repository;

import org.boldbit.illusionbackend.apigeneratorservice.model.APIDocumentsRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface APIDocumentsRegistryRepository extends MongoRepository<APIDocumentsRegistry, String> {
}
