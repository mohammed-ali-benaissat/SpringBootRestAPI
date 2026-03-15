package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super(Constants.ORDER_NOT_FOUND + id);
    }
}
