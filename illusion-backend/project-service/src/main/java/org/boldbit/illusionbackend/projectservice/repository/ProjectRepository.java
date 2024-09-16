package org.boldbit.illusionbackend.projectservice.repository;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findByOwnerIdAndName(String ownerId, String name);

    List<Project> findAllByOwnerId(String ownerId);
}
