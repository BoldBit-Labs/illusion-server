package org.boldbit.illusionbackend.userservice.repository;

import org.boldbit.illusionbackend.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<User,String> {

}
