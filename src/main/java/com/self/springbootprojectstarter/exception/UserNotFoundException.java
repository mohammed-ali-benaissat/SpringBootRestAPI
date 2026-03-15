package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(Constants.USER_NOT_FOUND + id);
    }
}
