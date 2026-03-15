package com.self.springbootprojectstarter.controller;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // ------------------- GET ALL -------------------
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ------------------- CREATE -------------------
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = userService.addUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto updatedUserDto){
        UserDto updatedUser = userService.updateUser(id, updatedUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------- CHECK EXISTENCE -------------------
    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> nameExists(@PathVariable String name) {
        return ResponseEntity.ok(userService.checkIfUserExists(name));
    }
}