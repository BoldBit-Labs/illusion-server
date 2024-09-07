package org.boldbit.illusionbackend.projectservice.service;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProjectService {
    String createProject(Project project);
    Optional<Project> findProjectByOwnerIdAndProjectName(String ownerId, String projectName);
    Project getProject(String projectId);
    List<Project> getAllProjects(String ownerId);
//    void updateProject(String projectId);
//    void deleteProject(String projectId);
}
