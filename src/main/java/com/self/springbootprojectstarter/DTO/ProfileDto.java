package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.NotBlank;

public class ProfileDto {
    // Id exposed to clients for read operations; not required on create.
    private Long id;

    // Short biography text validated at the API boundary.
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
