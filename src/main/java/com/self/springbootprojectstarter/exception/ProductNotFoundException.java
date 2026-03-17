package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class ProductNotFoundException extends RuntimeException {
    // Thrown when a product id cannot be found in persistence.
    public ProductNotFoundException(Long id) {
        super(Constants.PRODUCT_NOT_FOUND + id);
    }
}
