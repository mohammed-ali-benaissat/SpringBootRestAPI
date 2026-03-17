package com.self.springbootprojectstarter.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderDto {
    private Long id;

    @NotBlank(message = "Product cannot be empty")
    private String product;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    public OrderDto() {}

    public OrderDto(String product, Long userId) {
        this.product = product;
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
