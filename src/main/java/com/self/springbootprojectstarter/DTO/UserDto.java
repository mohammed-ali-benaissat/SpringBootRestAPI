package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class UserDto {
    // Id exposed to clients for read operations; not required on create.
    private Long id;

    // Basic user fields validated at the API boundary.
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(regexp = ".+@.+\\..+", message = "Email must be valid")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be 1 or higher")
    @Max(value = 150, message = "Age must be 150 or lower")
    private Integer age;

    // Relationship represented as id to keep DTOs simple and stable.
    private Long profileId;

    public UserDto(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public UserDto() {}

    //Getters

    public String getName(){
        return name;
    }

    public Integer getAge() {return age;}

    public String getEmail() {return email;}

    public Long getProfileId() {
        return profileId;
    }
    public Long getId() { return id; }

    //Setters


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
