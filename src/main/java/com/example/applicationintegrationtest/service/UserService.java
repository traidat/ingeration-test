package com.example.applicationintegrationtest.service;

import com.example.applicationintegrationtest.model.User;
import com.example.applicationintegrationtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isValidUser(User user) {
        return user.getUsername().matches("^[a-zA-Z0-9]*$") && userRepository.findByUsername(user.getUsername()).isEmpty();
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


}
