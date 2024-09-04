package org.boldbit.illusionbackend.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.model.User;
import org.boldbit.illusionbackend.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    private void signUp(@RequestBody User user) {
        try {
            userService.createUser(user);
            log.info("Created user: {}", user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
    private void signIn(@RequestBody SignInDTO signInDTO) {
        try {
            userService.signIn(signInDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    private User getCurrentUser(@PathVariable String id){
        try {
            return userService.getUser(id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

