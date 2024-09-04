package org.boldbit.illusionbackend.projectservice.service;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    void createProject(String projectName, String projectDescription);
    void getProject(String projectId);
    void getAllProjects();
    void updateProject(String projectId);
    void deleteProject(String projectId);
}
