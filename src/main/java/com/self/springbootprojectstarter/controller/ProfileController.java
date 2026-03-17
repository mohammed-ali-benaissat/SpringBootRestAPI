package com.self.springbootprojectstarter.controller;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // ------------------- GET ALL -------------------
    @GetMapping
    // Returns all profiles (DTOs only).
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    // Fetches a single profile or triggers ProfileNotFoundException.
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    // ------------------- CREATE -------------------
    @PostMapping
    // Validates request body and returns the created profile DTO.
    public ResponseEntity<ProfileDto> addProfile(@Valid @RequestBody ProfileDto dto) {
        ProfileDto createdProfile = profileService.addProfile(dto);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    // Updates existing profile; id is taken from the path.
    public ResponseEntity<Void> updateProfile(@PathVariable Long id, @Valid @RequestBody ProfileDto dto) {
        profileService.updateProfile(id, dto);
        return ResponseEntity.noContent().build();
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    // Deletes profile and returns 204 if successful.
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
