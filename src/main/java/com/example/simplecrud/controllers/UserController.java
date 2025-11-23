package com.example.simplecrud.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.simplecrud.models.User;
import com.example.simplecrud.services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServices userServices) {
        log.info("UserController initialized with UserServices");
        this.userServices = userServices;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("Fetching user with id: {}", id);
        return userServices.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating user: {}", user);
        User newUser = userServices.addUser(user.name(), user.email());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newUser.id())
            .toUri();
        
        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok(userServices.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("Deleting user with id: {}", id);
        boolean deleted = userServices.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody User user) {
        boolean updated = userServices.updateUser(id, user.name(), user.email());
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
