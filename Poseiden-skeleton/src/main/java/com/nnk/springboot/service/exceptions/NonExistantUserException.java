package com.nnk.springboot.service.exceptions;

public class NonExistantUserException extends Exception{
    public NonExistantUserException(String message) {
        super(message);
    }
}
