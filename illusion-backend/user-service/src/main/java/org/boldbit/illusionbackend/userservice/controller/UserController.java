package org.boldbit.illusionbackend.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.model.User;
import org.boldbit.illusionbackend.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    private  ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            String userId = userService.createUser(user);
            if (userId != null) {
                return ResponseEntity.ok().body(userId);
            }
            log.info("Created user: {}", user);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signin")
    private ResponseEntity<?> signIn(@RequestBody SignInDTO signInDTO) {
        try {
            String userId = userService.signIn(signInDTO);
            if (userId != null) {
                return ResponseEntity.ok().body(userId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    private User getCurrentUser(@PathVariable String id){
        try {
            return userService.getUser(id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/projects/{userId}")
    private String addProjectId(@PathVariable String userId, @RequestBody String projectId) {
        try {
            return userService.addProjectID(userId, projectId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

