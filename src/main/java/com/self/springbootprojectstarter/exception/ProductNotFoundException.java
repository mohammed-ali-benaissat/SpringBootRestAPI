package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(Constants.PRODUCT_NOT_FOUND + id);
    }
}
