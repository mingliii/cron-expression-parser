package com.interview;

public class NotvalidCronExpressionException extends RuntimeException{

    public NotvalidCronExpressionException(String expression, String reason, Exception e) {
        super(errorMsg(expression, reason), e);
    }

    public NotvalidCronExpressionException(String expression, String reason) {
        super(errorMsg(expression, reason));
    }

    private static String errorMsg(String expression, String reason) {
        return String.format("Invalid expression '%s' - reason: %s", expression, reason);
    }
}
