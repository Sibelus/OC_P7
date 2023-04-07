package com.nnk.springboot.service.exceptions;

public class NonExistantTradeException extends Exception {
    public NonExistantTradeException(String message) {
        super(message);
    }
}
