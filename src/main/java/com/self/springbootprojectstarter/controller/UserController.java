package com.self.springbootprojectstarter.controller;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.service.UserService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // ------------------- GET ALL USERS (PAGINATED) -------------------
    /**
     * Get all users with optional pagination and sorting
     * Example: GET /user?page=0&size=5&sort=name,asc
     *
     * @param pageable automatically mapped by Spring (page, size, sort)
     * @return paginated users as DTOs
     */
    @GetMapping
    // Returns a paginated page of users (DTOs only, no entities exposed).
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable
    ){
        Page<UserDto> usersPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersPage);
    }

    // ------------------- GET USER BY ID -------------------
    @GetMapping("/{id}")
    // Fetches a single user or triggers UserNotFoundException.
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ------------------- CREATE USER -------------------
    @PostMapping
    // Validates request body and returns the created user DTO.
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = userService.addUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // ------------------- UPDATE USER -------------------
    @PutMapping("/{id}")
    // Updates existing user; id is taken from the path.
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto updatedUserDto){
        UserDto updatedUser = userService.updateUser(id, updatedUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    // ------------------- DELETE USER -------------------
    @DeleteMapping("/{id}")
    // Deletes user and returns 204 if successful.
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------- CHECK USER EXISTENCE -------------------
    @GetMapping("/exists")
    // Lightweight existence check by name.
    public ResponseEntity<Boolean> exists(@RequestParam String name) {
        return ResponseEntity.ok(userService.checkIfUserExists(name));
    }
}
