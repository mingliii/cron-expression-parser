package com.interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CronExpression {

    // days in week - ignore the first one
    private static final int[] _WEEK = new int[8];
    // 12 months - ignore the first one
    private static final int[] _MONTH = new int[13];
    // 31 days in month - ignore the first one
    private static final int[] _DAY = new int[32];
    // 24 hours - ignore the first one
    private static final int[] _HOUR = new int[24];
    // 60 minutes in an hour
    private static final int[] _MINUTE = new int[60];

    static {
        init(0, _WEEK, _MONTH, _DAY);
        init(1, _HOUR, _MINUTE);
    }

    // Field types
    private static final int MINUTE = 0;
    private static final int HOUR = 1;
    private static final int DAY = 2;
    private static final int MONTH = 3;
    private static final int WEEK = 4;
    private static final int COMMAND = 5;

    // Mapping from field types to value ranges
    private static final Map<Integer, int[]> VALUES_MAP = new HashMap<>(5) {
        {
            put(MINUTE, _MINUTE);
            put(HOUR, _HOUR);
            put(DAY, _DAY);
            put(MONTH, _MONTH);
            put(WEEK, _WEEK);
        }
    };

    private final Map<Integer, int[]> RESULTS = new HashMap<>(6);


    private static void init(int start, int[]... values) {
        for (int[] value : values) {
            for (int i = start; i < value.length; i++) {
                value[i] = i;
            }
        }
    }

    public CronExpression(String expression) {
        buildExpression(expression);
    }

    protected void buildExpression(String expression) {
        String[] fields = expression.split(" ");
        if (fields.length != 6) {
            String errorMsg = String.format("Invalid expression: %s - %s", expression, "Missing field(s)");
            throw new InvalidExpressionException(errorMsg);
        }

        for (int i = 0; i < fields.length; i++) {
            parse(fields[i], i);
        }

    }

    private void parse(String field, int fieldType) {
        if (Objects.equals(field, "*")) {
            int[] range = VALUES_MAP.get(fieldType);
            if (fieldType != MINUTE && fieldType != HOUR) { // index needs to start from 1
                range = Arrays.copyOfRange(VALUES_MAP.get(fieldType), 1, range.length);
            }

            RESULTS.put(fieldType, range);
            return;
        }

        if (Objects.equals(field, "?")) {
            return;
        }

        if (field.contains("-")) {
            parseRange(field, fieldType);
        }

        if (field.contains("/") && fieldType != COMMAND) {
            parseInterval(field, fieldType);
        }

        if (field.contains(",")) {
            parseList(field, fieldType);
        }

        if (field.contains("L")) {
            handleLastOperator();
        }

        if (field.contains("W")) {
            handleWeekday();
        }
    }


    private void parseRange(String field, int fieldType) {
        final int[] values = VALUES_MAP.get(fieldType);
        String[] startEnd = field.split("-");
        if (startEnd.length != 2) {
            throw new InvalidExpressionException("The field is not valid");
        }

        try {
            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);
            RESULTS.put(fieldType, Arrays.copyOfRange(values, start, end + 1));
        } catch (Exception e) {
            throw new InvalidExpressionException("The field is not valid: " + field);
        }
    }

    private void parseInterval(String field, int fieldType) {
        final int[] values = VALUES_MAP.get(fieldType);
        final String[] startEnd = field.split("/");
        if (startEnd.length != 2) {
            throw new InvalidExpressionException("The field is not valid: " + field);
        }
        try {
            if (Objects.equals(startEnd[0], "*")) {
                int[] intervals = Arrays.stream(values).filter(val -> val % Integer.parseInt(startEnd[1]) == 0).toArray();
                RESULTS.put(fieldType, intervals);
                return;
            }

            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);
            int[] range = Arrays.copyOfRange(values, start, end + 1);
            int[] intervals = Arrays.stream(range).filter(val -> val % end == 0).toArray();
            RESULTS.put(fieldType, intervals);
        } catch (Exception e) {
            throw new InvalidExpressionException("The field is not valid: " + field);
        }
    }

    private void parseList(String field, int fieldType) {

    }

    private void handleLastOperator() {

    }

    private void handleWeekday() {

    }

    public static void main(String[] args) {
        CronExpression cronExpression = new CronExpression("*/15 0 1,15 * 1-5 /usr/bin/find");

        System.out.println(cronExpression);
    }
}
