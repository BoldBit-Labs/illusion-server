package org.boldbit.illusionbackend.projectservice.service;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.boldbit.illusionbackend.projectservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void createProject(String projectName, String description) {
        projectRepository.save(new Project("", projectName, description, new ArrayList<>()));
    }

    @Override
    public void getProject(String projectId) {
        projectRepository.findById(projectId).ifPresent(System.out::println);
    }

    @Override
    public void getAllProjects() {
        projectRepository.findAll().forEach(System.out::println);
    }

    @Override
    public void updateProject(String projectId) {

    }

    @Override
    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }
}
