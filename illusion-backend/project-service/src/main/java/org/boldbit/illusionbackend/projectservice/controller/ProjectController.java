package org.boldbit.illusionbackend.projectservice.controller;

import org.boldbit.illusionbackend.projectservice.model.Project;
import org.boldbit.illusionbackend.projectservice.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    ProjectService projectService;

    @PostMapping("/create-project")
    private ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            String projectId = projectService.createProject(project);
            if (projectId != null) {
                return ResponseEntity.ok().body(projectId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/{ownerId}")
    private ResponseEntity<?> getAllProjects(@PathVariable String ownerId) {
        try {
            List<Project> projects = projectService.getAllProjects(ownerId);
            return ResponseEntity.ok().body(projects);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/project/{projectId}")
    private ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        try {
            Project project = projectService.getProject(projectId);
            return ResponseEntity.ok().body(project);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body(null);
    }
}
