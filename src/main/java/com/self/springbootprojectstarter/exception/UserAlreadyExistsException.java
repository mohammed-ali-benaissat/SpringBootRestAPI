package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String name) {
        super(Constants.USER_ALREADY_EXISTS + name);
    }
}