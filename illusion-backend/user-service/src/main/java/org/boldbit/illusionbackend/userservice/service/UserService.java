package org.boldbit.illusionbackend.userservice.service;

import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.model.User;

public interface UserService {
    void createUser(User user);
    void signIn(SignInDTO signInDTO);
}
