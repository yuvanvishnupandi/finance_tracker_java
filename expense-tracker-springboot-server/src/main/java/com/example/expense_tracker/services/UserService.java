package com.example.expense_tracker.services;

import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    // get
    public Optional<User> getUserById(int userId){
        logger.info("Getting the user by id: " + userId);
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email){
        logger.info("Getting the user by email: " + email);
        return userRepository.findByEmail(email);
    }

    // post
    public User createUser(String name, String username, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(username);
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}












