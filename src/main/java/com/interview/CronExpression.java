package com.interview;

import java.util.*;

import static com.interview.CronExpression.FIELD_TYPE.*;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class CronExpression {

    // days in week - ignore the first one
    private static final int[] _DAYS_OF_WEEK = new int[8];

    // 12 months - ignore the first one
    private static final int[] _MONTHS = new int[13];
    // 31 days in month - ignore the first one
    private static final int[] _DAYS_OF_MONTH = new int[32];
    // 24 hours - ignore the first one
    private static final int[] _HOURS = new int[24];
    // 60 minutes in an hour
    private static final int[] _MINUTES = new int[60];

    static {
        init(1, _DAYS_OF_WEEK, _MONTHS, _DAYS_OF_MONTH);
        init(0, _HOURS, _MINUTES);
    }

    enum FIELD_TYPE {
        MINUTE,
        HOUR,
        DAY_OF_MONTH,
        MONTH,
        DAY_OF_WEEK,
        COMMAND
    }

    // Mapping from field types to value ranges
    private static final Map<FIELD_TYPE, int[]> VALUES_MAP = new HashMap<>(5) {
        {
            put(MINUTE, _MINUTES);
            put(HOUR, _HOURS);
            put(DAY_OF_MONTH, _DAYS_OF_MONTH);
            put(MONTH, _MONTHS);
            put(DAY_OF_WEEK, _DAYS_OF_WEEK);
        }
    };

    private final Map<FIELD_TYPE, String[]> parsedFields = new HashMap<>(6);
    private String command;
    private final String expression;

    private static void init(int start, int[]... values) {
        for (int[] value : values) {
            for (int i = start; i < value.length; i++) {
                value[i] = i;
            }
        }
    }

    public CronExpression(String expression) {
        this.expression = expression;
        buildExpression(expression);
    }

    void buildExpression(String expression) {
        String[] fields = expression.split(" ");
        if (fields.length != 6) {
            throw new NotvalidCronExpressionException(expression, "The number of fields should be 6, given: " + fields.length);
        }

        for (int type = 0; type < fields.length; type++) {
            if (type == COMMAND.ordinal()) {
                command = fields[type];
                continue;
            }
            parse(fields[type], FIELD_TYPE.values()[type]);
        }
    }

    void parse(String field, FIELD_TYPE fieldType) {

        // Match all values given field type
        if (Objects.equals(field, "*")) {
            String[] range = stream(VALUES_MAP.get(fieldType)).mapToObj(String::valueOf).toArray(String[]::new);
            if (fieldType != MINUTE && fieldType != HOUR) { // index needs to start from 1
                range = copyOfRange(range, 1, range.length);
            }

            parsedFields.put(fieldType, range);
            return;
        }

        // Match nothing
        if (Objects.equals(field, "?")) {
            return;
        }

        // Match a range
        if (field.contains("-")) {
            parsedFields.put(fieldType, parseRange(field, fieldType));
            return;
        }

        // Match given initial value and step
        if (field.contains("/")) {
            parsedFields.put(fieldType, parseInterval(field, fieldType));
            return;
        }

        // Match all listed values
        if (field.contains(",")) {
            parsedFields.put(fieldType, parseList(field));
            return;
        }

        if (field.contains("L")) {
            return;
        }

        if (field.contains("W")) {
            return;
        }

        try {
            parsedFields.put(fieldType, new String[]{String.valueOf(Integer.parseInt(field))});
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field), e);
        }
    }

    // Parse filed like 1-5, 2-10
    String[] parseRange(String field, FIELD_TYPE fieldType) {
        final String[] values = stream(VALUES_MAP.get(fieldType)).mapToObj(String::valueOf).toArray(String[]::new);
        String[] startEnd = field.split("-");
        if (startEnd.length != 2) {
            throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field));
        }

        try {
            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);
            return copyOfRange(values, start, end + 1);
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field));
        }
    }

    // Parse field like */5, 1/10
    String[] parseInterval(String field, FIELD_TYPE fieldType) {
        final int[] values = VALUES_MAP.get(fieldType);
        final String[] startEnd = field.split("/");
        if (startEnd.length != 2) {
            throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field));
        }

        try {
            int start = Objects.equals(startEnd[0], "*") ? 0 : Integer.parseInt(startEnd[0]);
            int interval = Integer.parseInt(startEnd[1]);
            if (start > values[values.length - 1] || interval < 1) {
                throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field));
            }

            int mod = (fieldType == MINUTE || fieldType == HOUR) ? values.length : values.length - 1;
            int mark = 0;

            List<Integer> results = new ArrayList<>();

            // [0...59]  1/45 => [1, 46, 31, 16]
            for (int i = mark; i < values.length; i++) {
                int val = values[i];
                if (val % interval == 0) {
                    mark = (val + start) >= mod ? (val + start - mod) % mod : val + start;
                    results.add(mark);
                }
            }
            while (!results.contains((mark + interval) % mod)) {
                mark = (mark + interval) % mod;
                results.add(mark);
            }
            return results.stream().map(String::valueOf).toArray(String[]::new);
        } catch (Exception e) {
            throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field), e);
        }
    }

    // Parse field like 1,2,3,4,5
    String[] parseList(String field) {
        try {
            String[] values = field.split(",");
            return stream(values).mapToInt(Integer::parseInt).distinct().mapToObj(String::valueOf).toArray(String[]::new);
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(expression, fieldErrorMsg(field), e);
        }
    }

    public String describe() {
        String minutes     = "minute         " + join(" ", getMinutes());
        String hours       = "hour           " + join(" ", getHours());
        String daysOfMonth = "day of month   " + join(" ", getDaysOfMonth());
        String months      = "month          " + join(" ", getMonths());
        String daysOfWeek  = "day of week    " + join(" ", getDaysOfWeek());
        String commandStr  = "command        " + command;
        return join("\n", List.of(minutes, hours, daysOfMonth, months, daysOfWeek, commandStr));
    }

    private String fieldErrorMsg(String field) {
        return format("The field '%s' is not valid", field);
    }

    public String[] getMinutes() {
        return parsedFields.get(MINUTE);
    }

    public String[] getHours() {
        return parsedFields.get(HOUR);
    }

    public String[] getDaysOfMonth() {
        return parsedFields.get(DAY_OF_MONTH);
    }

    public String[] getMonths() {
        return parsedFields.get(MONTH);
    }

    public String[] getDaysOfWeek() {
        return parsedFields.get(DAY_OF_WEEK);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide one cron express only!");
            return;
        }
        CronExpression cronExpression = new CronExpression(args[0]);
        String output = cronExpression.describe();
        System.out.println(output);
    }
}
