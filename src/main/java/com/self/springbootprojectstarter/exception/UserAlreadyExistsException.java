package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class UserAlreadyExistsException extends RuntimeException {
    // Thrown when creating/updating a user with a duplicate unique field (email).
    public UserAlreadyExistsException(String name) {
        super(Constants.USER_ALREADY_EXISTS + name);
    }
}
