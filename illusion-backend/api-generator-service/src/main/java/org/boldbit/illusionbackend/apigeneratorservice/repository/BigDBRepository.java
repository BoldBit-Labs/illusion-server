package org.boldbit.illusionbackend.apigeneratorservice.repository;

import org.boldbit.illusionbackend.apigeneratorservice.model.BigDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BigDBRepository extends MongoRepository<BigDB, String> {
}
