package com.self.springbootprojectstarter.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(regexp = ".+@.+\\..+", message = "Email must be valid")
    @Column(unique = true)
    private String email;

    private int age;

    private Long profileId;

    public UserDto(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public UserDto() {}

    //Getters

    public String getName(){
        return name;
    }

    public int getAge() {return age;}

    public String getEmail() {return email;}

    public Long getProfileId() {
        return profileId;
    }

    //Setters


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
