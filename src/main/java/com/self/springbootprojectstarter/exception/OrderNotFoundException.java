package com.self.springbootprojectstarter.exception;

import com.self.springbootprojectstarter.config.Constants;

public class OrderNotFoundException extends RuntimeException {
    // Thrown when an order id cannot be found in persistence.
    public OrderNotFoundException(Long id) {
        super(Constants.ORDER_NOT_FOUND + id);
    }
}
