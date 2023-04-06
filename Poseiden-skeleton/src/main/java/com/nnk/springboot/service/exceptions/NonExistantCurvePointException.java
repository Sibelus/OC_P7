package com.nnk.springboot.service.exceptions;

public class NonExistantCurvePointException extends Exception {
    public NonExistantCurvePointException(String message) {
        super(message);
    }
}
