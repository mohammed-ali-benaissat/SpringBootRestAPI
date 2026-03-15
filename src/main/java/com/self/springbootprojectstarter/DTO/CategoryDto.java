package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
    @NotBlank(message = "Category name cannot be empty")
    private String name;

    public CategoryDto() {}

    public CategoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
