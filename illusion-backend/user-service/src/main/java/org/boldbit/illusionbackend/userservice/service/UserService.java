package org.boldbit.illusionbackend.userservice.service;

import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.model.User;

public interface UserService {
    String createUser(User user);
    String signIn(SignInDTO signInDTO);
    User getUser(String id);
    String addProjectID(String userId, String projectId);
}
