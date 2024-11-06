package com.fractalis.greentoolswebservice.account.interfaces.rest;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserCommandService;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@CrossOrigin(origins = "**", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "All user related endpoints")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    /**
     * Constructor
     *
     * @param userCommandService The {@link UserCommandService} service
     * @param userQueryService The {@link UserQueryService} service
     */
    @Autowired
    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Get all the users
     *
     * @return The list of {@link User} users
     */
    @Operation(summary = "Get all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users found")})
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userQueryService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user by its id
     *
     * @param id The user id
     * @return The object {@link User} user
     */
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User found")})
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userQueryService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Post a user
     *
     * @param userRequest User body
     * @return The just created {@link User} user
     */
    @Operation(summary = "Create user")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User created")})
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

    /**
     * Update an existing user
     *
     * @param userRequest User Body
     * @param id The user id
     * @return The object {@link User} user
     */
    @Operation(summary = "Update user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User updated")})
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

    /**
     * Delete a user
     *
     * @param id The user id
     * @return The object {@link HttpStatus} status
     */
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "User deleted")})
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