package org.boldbit.illusionbackend.projectservice.controller;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.projectservice.model.Project;
import org.boldbit.illusionbackend.projectservice.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create-project")
    public ResponseEntity<String> createProject(@RequestBody Project project) {
        String projectId = projectService.createProject(project);
        return ResponseEntity.ok(projectId);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable String projectId) {
        Project project = projectService.getProject(projectId);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable String ownerId) {
        List<Project> projects = projectService.getAllProjects(ownerId);
        return ResponseEntity.ok(projects);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable String projectId, @RequestBody Map<String, Object> updates) {
        Project updatedProject = projectService.updateProject(projectId, updates);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable String projectId) {
        boolean deleted = projectService.deleteProject(projectId);
        if (deleted) {
            return ResponseEntity.ok("Project deleted successfully");
        }
        return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
    }
}
