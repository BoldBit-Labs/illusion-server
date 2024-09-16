package org.boldbit.illusionbackend.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.exceptions.UserNotFoundException;
import org.boldbit.illusionbackend.userservice.model.User;
import org.boldbit.illusionbackend.userservice.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String createUser(User user) {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateKeyException("Email already exists: " + user.getEmail());
            }
            return userRepository.save(user).getUserId();
        } catch (DuplicateKeyException e) {
            log.error("Error creating user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error creating user: {}", e.getMessage());
            throw new RuntimeException("Failed to create user", e);
        }
    }

    public String signIn(SignInDTO signInDTO) {
        User user = userRepository.findByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email " + signInDTO.getEmail() + " not found"));

        if (!signInDTO.getPassword().equals(user.getPassword())) {
            log.warn("Incorrect password for email: {}", signInDTO.getEmail());
            throw new IllegalArgumentException("Invalid password");
        }

        log.info("Sign in successful for user: {}", user.getEmail());
        return user.getUserId();
    }

    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    public boolean deleteUser(String userId, Map<String, String> password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getPassword().equals(password.get("password"))) {
            userRepository.delete(user);
            log.info("User deleted with ID: {}", userId);
            return true;
        }
        throw new UserNotFoundException("incorrect password");
    }

    public User updateUser(String userId, Map<String, Object> updates) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        updates.forEach((field, value) -> {
            switch (field) {
                case "fullName":
                    user.setFullName((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                case "projects":
                    if (value instanceof Map) {
                        updateProjects(user, (Map<String, String>) value);
                    } else if (value instanceof String) {
                        removeProject(user, (String) value);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown update field: " + field);
            }
        });

        userRepository.save(user);
        log.info("User with ID {} updated successfully", userId);
        return user;
    }

    private void updateProjects(User user, Map<String, String> updates) {
        List<User.Project> projects = Optional.ofNullable(user.getProjects()).orElse(new ArrayList<>());

        boolean projectExists = projects.stream()
                .anyMatch(proj -> proj.getProjectName().equals(updates.get("projectName")));

        if (projectExists) {
            log.warn("Project with name '{}' already exists", updates.get("projectName"));
            throw new IllegalArgumentException("Project already exists");
        }

        projects.add(new User.Project(updates.get("projectId"), updates.get("projectName")));
        user.setProjects(projects);
        userRepository.save(user);
        log.info("Project added successfully");
    }

    private void removeProject(User user, String projectId) {
        List<User.Project> projects = Optional.ofNullable(user.getProjects()).orElse(new ArrayList<>());

        List<User.Project> updatedProjects = projects.stream()
                .filter(project -> !project.getProjectId().equals(projectId))
                .toList();

        if (updatedProjects.size() == projects.size()) {
            log.warn("Project with ID '{}' not found", projectId);
            throw new RuntimeException("Project not found");
        }

        user.setProjects(updatedProjects);
        log.info("Project with ID '{}' removed successfully", projectId);
    }
}
