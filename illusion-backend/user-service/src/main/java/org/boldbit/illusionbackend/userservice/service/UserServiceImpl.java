package org.boldbit.illusionbackend.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.model.User;
import org.boldbit.illusionbackend.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void signIn(SignInDTO signInDTO) {
        User user = userRepository.findByEmail(signInDTO.getEmail()).orElse(null);
        if (user != null && signInDTO.getPassword().equals(user.getPassword())) {
            log.info("Sign in successful");
        } else {
            log.info("Sign in failed");
        }
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }
}
