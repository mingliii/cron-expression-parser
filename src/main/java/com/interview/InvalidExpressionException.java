package com.interview;

public class InvalidExpressionException extends RuntimeException{
    public InvalidExpressionException(String errorMsg) {
        super(errorMsg);
    }
}
