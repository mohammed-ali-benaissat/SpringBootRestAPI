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

    /**
     * 🔹 ProfileController
     *
     * Teaching points:
     * 1️⃣ Handles HTTP requests for Profile
     * 2️⃣ Uses ProfileService to perform CRUD operations
     * 3️⃣ Returns DTOs in API responses
     * 4️⃣ Uses @Valid to enforce DTO validations
     *
     * 🔗 How Profile Relates to User
     *
     * Entity Relation:
     *
     * User.profile -> ManyToOne (each User has one Profile)
     * Profile.users -> OneToMany (Profile can have multiple users)
     *
     * DTO Handling:
     *
     * UserDto.profileId stores the ID of the Profile
     *
     * When creating/updating a User, UserService fetches the Profile entity using ProfileService.getProfileEntityById(id)
     *
     * Service Layer Flow:
     *
     * UserController → UserService → ProfileService (fetch Profile) → UserRepository → DB
     *
     * Mapper Layer:
     *
     * UserMapper.toDto() maps User.profile.id → UserDto.profileId
     *
     * Keeps API safe from exposing full Profile object
     */

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
