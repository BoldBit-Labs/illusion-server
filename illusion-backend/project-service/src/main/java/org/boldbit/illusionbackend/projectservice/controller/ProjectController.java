package org.boldbit.illusionbackend.projectservice.controller;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.boldbit.illusionbackend.projectservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/api/create-project")
    private void createProject(@RequestBody Project project) {
        try {
            projectService.createProject(project.getName(), project.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
