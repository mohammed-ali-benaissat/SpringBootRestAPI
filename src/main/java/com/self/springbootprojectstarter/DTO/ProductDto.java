package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.NotBlank;

public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    public ProductDto() {}

    public ProductDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
