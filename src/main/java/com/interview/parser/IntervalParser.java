package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.interview.parser.FieldParser.FieldType.*;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

public class IntervalParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains("/") && fieldType != COMMAND;
    }

    // handle cases like below
    // [0...59]  1/45 => [1, 46, 31, 16]
    // [0...59]  0/15 => [0, 15, 30, 45]
    // [1...12]  */1 => [1, 2 ... 12]
    // [1...7]  */1 => [1, 2 ... 7]
    @Override
    public List<String> doParse(String field, FieldType fieldType) {
        int[] range = VALUES_MAP.get(fieldType);
        final String[] startEnd = field.split("/");

        if (startEnd.length != 2) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field));
        }

        try {
            int start;
            int current;
            int interval = parseInt(startEnd[1].trim()) % range.length;
            if (Objects.equals(startEnd[0].trim(), "*")) {
                current = 0;
            } else {
                try {
                    start = parseInt(startEnd[0].trim());
                    rangeValidator.validate(field, range, start);
                    int offset = fieldType == MINUTE || fieldType == HOUR ? 0 : 1;
                    current = start - offset;
                } catch (NumberFormatException e) {
                    if (fieldType == DAY_OF_WEEK) {
                        rangeValidator.validate(field, _DAYS_OF_WEEK_DDD, startEnd[0].trim());
                        current = _DAYS_OF_WEEK_DDD.indexOf(startEnd[0].trim());
                        return generateList(_DAYS_OF_WEEK_DDD, current, interval);
                    } else if (fieldType == MONTH) {
                        rangeValidator.validate(field, _MONTH_MMM, startEnd[0].trim());
                        current = _MONTH_MMM.indexOf(startEnd[0].trim());
                        return generateList(_MONTH_MMM, current, interval);
                    } else {
                        throw new NotValidCronExpressionException(fieldErrorMsg(field));
                    }
                }
            }

            return generateList(stream(range).boxed().map(String::valueOf).collect(Collectors.toList()), current, interval);
        } catch (Exception e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field), e);
        }
    }

    private <T> List<T> generateList(List<T> range, int start, int interval) {
        int current = start;
        List<T> results = new ArrayList<>();
        while (!results.contains(range.get(current))) {
            results.add(range.get(current));
            current = current + interval;
            current = current >= range.size() ? (current - range.size()) : current;
        }

        return results;
    }
}
