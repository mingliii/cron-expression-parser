package com.interview;

import com.interview.parser.FieldParser.FieldType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.interview.parser.FieldParser.FieldType.*;
import static java.lang.String.join;

public class CronExpressionResults {

    private final Map<FieldType, List<String>> parsedFields = new HashMap<>(6);

    public List<String> getMinutes() {
        return parsedFields.get(MINUTE);
    }

    public List<String> getHours() {
        return parsedFields.get(HOUR);
    }

    public List<String> getDaysOfMonth() {
        return parsedFields.get(DAY_OF_MONTH);
    }

    public List<String> getMonths() {
        return parsedFields.get(MONTH);
    }

    public List<String> getDaysOfWeek() {
        return parsedFields.get(DAY_OF_WEEK);
    }

    public String getCommand() {
        return parsedFields.get(COMMAND).get(0);
    }

    public void set(FieldType fieldType, List<String> values) {
        parsedFields.put(fieldType, values);
    }

    public String describe() {
        String minutes     = "minute         " + join(" ", getMinutes());
        String hours       = "hour           " + join(" ", getHours());
        String daysOfMonth = "day of month   " + join(" ", getDaysOfMonth());
        String months      = "month          " + join(" ", getMonths());
        String daysOfWeek  = "day of week    " + join(" ", getDaysOfWeek());
        String command  = "command        " + getCommand();
        return join("\n", List.of(minutes, hours, daysOfMonth, months, daysOfWeek, command));
    }

    @Override
    public String toString() {
        return describe();
    }
}
