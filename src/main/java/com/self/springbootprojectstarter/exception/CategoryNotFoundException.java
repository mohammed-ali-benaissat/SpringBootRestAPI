package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super(Constants.CATEGORY_NOT_FOUND + id);
    }
}
