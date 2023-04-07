package com.nnk.springboot.service.exceptions;

public class NonExistantRuleNameException extends Exception {
    public NonExistantRuleNameException(String message) {
        super(message);
    }
}
