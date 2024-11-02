package com.fractalis.greentoolswebservice.account.interfaces.rest;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserCommandService;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import com.fractalis.greentoolswebservice.account.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Autowired
    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userQueryService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userQueryService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User userRequest) {
        User user = userCommandService.createUser(
                userRequest.getName().firstName(),
                userRequest.getName().lastName(),
                userRequest.getEmail().address(),
                userRequest.getAddress().street(),
                userRequest.getAddress().number(),
                userRequest.getAddress().city(),
                userRequest.getAddress().zipCode(),
                userRequest.getAddress().country(),
                userRequest.getProfileImage()
        );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Actualizar un usuario existente (por ID)
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userRequest) {
        Optional<User> existingUser = userQueryService.getUserById(id);
        if (existingUser.isPresent()) {
            User updatedUser = userCommandService.updateUser(
                    id,
                    userRequest.getName().firstName(),
                    userRequest.getName().lastName(),
                    userRequest.getEmail().address(),
                    userRequest.getAddress().street(),
                    userRequest.getAddress().number(),
                    userRequest.getAddress().city(),
                    userRequest.getAddress().zipCode(),
                    userRequest.getAddress().country()
            );
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un usuario (por ID)
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> existingUser = userQueryService.getUserById(id);
        if (existingUser.isPresent()) {
            userCommandService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}