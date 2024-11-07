package com.example.platform.service;

import com.example.platform.model.User;
import com.example.platform.repository.UserRepository;
import com.example.platform.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null || !passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new UserNotFoundException("Invalid username or password");
        }
        // Generate JWT token
        return "JWT_TOKEN";
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
