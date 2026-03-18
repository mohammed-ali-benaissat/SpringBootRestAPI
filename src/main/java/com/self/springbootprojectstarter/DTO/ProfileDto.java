package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * 🔹 ProfileDto
 *
 * Teaching points:
 * 1️⃣ Used to send Profile data to clients safely
 * 2️⃣ Exposes only necessary fields (id, bio) instead of full entity
 * 3️⃣ Validation annotations ensure API receives correct data
 */
public class ProfileDto {

    // Exposed to clients for reading; not required when creating
    private Long id;

    @NotBlank(message = "Bio cannot be empty")
    // Ensures that client cannot send empty bio
    private String bio;

    public ProfileDto() {}
    public ProfileDto(String bio) { this.bio = bio; }

    // =================== GETTERS & SETTERS ===================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}