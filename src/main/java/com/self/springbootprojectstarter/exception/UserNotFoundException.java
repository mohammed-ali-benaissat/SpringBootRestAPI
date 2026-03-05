package com.self.springbootprojectstarter.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id){
        //user super key to get string value?
        super("person with id "+id+" not found");
    }
}
