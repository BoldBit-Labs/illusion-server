package org.boldbit.illusionbackend.projectservice.service;

import org.boldbit.illusionbackend.projectservice.clients.UserClient;
import org.boldbit.illusionbackend.projectservice.model.Project;
import org.boldbit.illusionbackend.projectservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserClient userClient;

    @Override
    @Transactional
    public String createProject(Project project) {
        Optional<Project> existingProject = findProjectByOwnerIdAndProjectName(project.getOwnerId(), project.getName());
        if (existingProject.isPresent()) {
            throw new RuntimeException("Project with the same name already exists");
        }
        String projectId = projectRepository.save(project).getId();
        return userClient.addProjectId(project.getOwnerId(), projectId);
    }

    @Override
    public Optional<Project> findProjectByOwnerIdAndProjectName(String ownerId, String projectName) {
        Optional<Project> existingProject = projectRepository.findByOwnerIdAndName(ownerId, projectName);
        return existingProject;
    }

    @Override
    public Project getProject(String projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new RuntimeException("Project with id " + projectId + " not found");
        }
    }

    @Override
    public List<Project> getAllProjects(String ownerId) {
        return projectRepository.findAllByOwnerId(ownerId);
    }
}
