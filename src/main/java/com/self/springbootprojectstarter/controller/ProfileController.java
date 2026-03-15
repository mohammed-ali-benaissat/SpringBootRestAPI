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
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    // ------------------- CREATE -------------------
    @PostMapping
    public ResponseEntity<Void> addProfile(@Valid @RequestBody ProfileDto dto) {
        profileService.addProfile(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfile(@PathVariable Long id, @Valid @RequestBody ProfileDto dto) {
        profileService.updateProfile(id, dto);
        return ResponseEntity.noContent().build();
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}