package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class ProfileNotFoundException extends RuntimeException {
    // Thrown when a profile id cannot be found in persistence.
    public ProfileNotFoundException(Long id) {
        super(Constants.PROFILE_NOT_FOUND + id);
    }
}
