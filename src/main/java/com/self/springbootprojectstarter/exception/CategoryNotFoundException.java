package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class CategoryNotFoundException extends RuntimeException {
    // Thrown when a category id cannot be found in persistence.
    public CategoryNotFoundException(Long id) {
        super(Constants.CATEGORY_NOT_FOUND + id);
    }
}
