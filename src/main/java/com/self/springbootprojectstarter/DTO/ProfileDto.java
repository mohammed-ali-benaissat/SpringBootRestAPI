package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.NotBlank;

public class ProfileDto {
    @NotBlank(message = "Bio cannot be empty")
    private String bio;

    public ProfileDto() {}

    public ProfileDto(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
