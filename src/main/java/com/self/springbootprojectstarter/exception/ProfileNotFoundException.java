package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long id) {
        super(Constants.PROFILE_NOT_FOUND + id);
    }
}
