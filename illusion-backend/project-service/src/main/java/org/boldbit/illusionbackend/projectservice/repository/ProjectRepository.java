package org.boldbit.illusionbackend.projectservice.repository;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

}
