package com.self.springbootprojectstarter.controller;

// Importing UserDto (Data Transfer Object)
// We NEVER expose Entity directly to the outside world
import com.self.springbootprojectstarter.DTO.UserDto;

// Service layer (business logic)
import com.self.springbootprojectstarter.service.UserService;

// Used for validation (@Valid)
import jakarta.validation.Valid;

// Used for Swagger/OpenAPI documentation (optional but good)
import org.springdoc.core.annotations.ParameterObject;

// Pagination classes from Spring
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Used to define default pagination values
import org.springframework.data.web.PageableDefault;

// Used to control HTTP responses (status codes + body)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// REST annotations
import org.springframework.web.bind.annotation.*;


// This tells Spring: "This class handles REST requests"
// It automatically converts responses to JSON
@RestController

// Base URL for all endpoints in this controller
// All endpoints will start with: /users
@RequestMapping("/users")
public class UserController {

    // We use the Service layer, NOT the Repository directly
    // This keeps architecture clean (Controller → Service → Repository)
    private final UserService userService;

    // Constructor Injection (BEST PRACTICE)
    // Spring automatically injects UserService here
    public UserController(UserService userService){
        this.userService = userService;
    }

    // ============================================================
    // ✅ GET ALL USERS (WITH PAGINATION)
    // ============================================================

    /**
     * 🔥 WHAT IS PAGINATION?
     *
     * Imagine you have 1,000,000 users in your database.
     * If you return ALL of them → VERY SLOW ❌
     *
     * Instead, we return small chunks called "pages".
     *
     * Example:
     * page=0 → first 10 users
     * page=1 → next 10 users
     *
     * URL Example:
     * GET /users?page=0&size=5&sort=name,asc
     *
     * page = page number (starts from 0)
     * size = number of items per page
     * sort = field + direction
     */

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(

            // @ParameterObject → helps Swagger understand Pageable
            @ParameterObject

            // Default values if client doesn't provide them
            @PageableDefault(page = 0, size = 10, sort = "name")

            // Spring automatically creates this object from request params
            Pageable pageable
    ){

        // We call the service layer
        Page<UserDto> usersPage = userService.getAllUsers(pageable);

        // Return HTTP 200 OK with data
        return ResponseEntity.ok(usersPage);
    }

    // ============================================================
    // ✅ GET USER BY ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(

            // Extracts "id" from URL → /users/5
            @PathVariable Long id
    ){

        // If user doesn't exist → exception will be thrown (handled globally)
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ============================================================
    // ✅ CREATE USER
    // ============================================================

    @PostMapping
    public ResponseEntity<UserDto> addUser(

            // @RequestBody → converts JSON → Java object
            // @Valid → applies validation rules from DTO
            @Valid @RequestBody UserDto userDto
    ){

        // Call service to save user
        UserDto createdUser = userService.addUser(userDto);

        // Return HTTP 201 CREATED (important for POST)
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // ============================================================
    // ✅ UPDATE USER
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(

            // ID comes from URL
            @PathVariable Long id,

            // Updated data comes from request body
            @Valid @RequestBody UserDto updatedUserDto
    ){

        // Service handles update logic
        UserDto updatedUser = userService.updateUser(id, updatedUserDto);

        return ResponseEntity.ok(updatedUser);
    }

    // ============================================================
    // ✅ DELETE USER
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(

            @PathVariable Long id
    ){

        // Service deletes the user
        userService.deleteUser(id);

        // Return HTTP 204 NO CONTENT (no body)
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // ✅ CHECK IF USER EXISTS
    // ============================================================

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(

            // Example: /users/exists?name=John
            @RequestParam String name
    ) {

        // Lightweight check (returns true/false)
        return ResponseEntity.ok(userService.checkIfUserExists(name));
    }
}