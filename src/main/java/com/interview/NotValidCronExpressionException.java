package com.interview;

public class NotValidCronExpressionException extends RuntimeException{

    public NotValidCronExpressionException(String expression, String reason) {
        super(errorMsg(expression, reason));
    }

    public NotValidCronExpressionException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }

    public NotValidCronExpressionException(String errorMsg) {
        super(errorMsg);
    }

    private static String errorMsg(String expression, String reason) {
        return String.format("Invalid expression '%s' - reason: %s", expression, reason);
    }
}
