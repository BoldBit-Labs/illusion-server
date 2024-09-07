package org.boldbit.illusionbackend.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.boldbit.illusionbackend.userservice.dto.SignInDTO;
import org.boldbit.illusionbackend.userservice.model.User;
import org.boldbit.illusionbackend.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUser(User user) {
        return userRepository.save(user).getUserId();
    }

    @Override
    public String signIn(SignInDTO signInDTO) {
        User user = userRepository.findByEmail(signInDTO.getEmail()).orElse(null);
        if (user != null && signInDTO.getPassword().equals(user.getPassword())) {
            log.info("Sign in successful");
            return user.getUserId();
        } else {
            log.info("Sign in failed");
        }
        return null;
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addProjectID(String userId, String projectId) {
        User user = userRepository.findById(userId).orElse(null);

        assert user != null;
        List<String> projectIds = user.getProjectIds();
        if (projectIds == null) {
            projectIds = new ArrayList<>();
            user.setProjectIds(projectIds);
        }

        if (!projectIds.contains(projectId)) {
            projectIds.add(projectId);
            userRepository.save(user);
        }
    }
}
