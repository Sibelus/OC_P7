package com.nnk.springboot.service.exceptions;

public class NonExistantBidlistException extends Exception {
    public NonExistantBidlistException(String message) {
        super(message);
    }
}
