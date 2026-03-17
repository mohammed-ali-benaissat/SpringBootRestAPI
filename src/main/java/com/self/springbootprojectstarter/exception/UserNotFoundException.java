package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class UserNotFoundException extends RuntimeException {
    // Thrown when a user id cannot be found in persistence.
    public UserNotFoundException(Long id) {
        super(Constants.USER_NOT_FOUND + id);
    }
}
