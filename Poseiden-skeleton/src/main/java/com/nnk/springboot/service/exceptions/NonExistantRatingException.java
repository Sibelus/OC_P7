package com.nnk.springboot.service.exceptions;

public class NonExistantRatingException extends Exception {
    public NonExistantRatingException(String message) {
        super(message);
    }
}
