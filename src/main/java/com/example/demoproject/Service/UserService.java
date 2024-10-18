package com.example.demoproject.Service;


import java.util.List;

import com.example.demoproject.Entity.User;
import com.example.demoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public ResponseEntity<User> getUserById(Long id) {
        return (ResponseEntity)this.userRepository.findById(id).map((user) -> {
            return ResponseEntity.ok().body(user);
        }).orElse(ResponseEntity.notFound().build());
    }

    public User createUser(User user) {
        return (User)this.userRepository.save(user);
    }

    public ResponseEntity<User> updateUser(Long id, User userDetails) {
        return (ResponseEntity)this.userRepository.findById(id).map((user) -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPasswordHash(userDetails.getPasswordHash());
            user.setCreatedAt(userDetails.getCreatedAt());
            return ResponseEntity.ok((User)this.userRepository.save(user));
        }).orElseThrow(() -> {
            return new RuntimeException("User not found");
        });
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        return (ResponseEntity)this.userRepository.findById(id).map((user) -> {
            this.userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> {
            return new RuntimeException("User not found");
        });
    }
}

