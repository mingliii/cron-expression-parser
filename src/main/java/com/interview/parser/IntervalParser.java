package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.interview.parser.FieldParser.FieldType.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

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
            if (Objects.equals(startEnd[0].trim(), "*")) {
                start = range[0];
            } else {
                start = parseInt(startEnd[0].trim());
                if (!rangeValidator.validate(range, start)) {
                    throw new NotValidCronExpressionException(fieldErrorMsg(field));
                }
            }

            int interval = parseInt(startEnd[1].trim()) % range.length;

            List<String> results = new ArrayList<>();
            int offset = fieldType == MINUTE || fieldType == HOUR ? 0 : 1;
            int current = start - offset;
            while (!results.contains(valueOf(range[current]))) {
                results.add(valueOf(range[current]));
                current = current + interval;
                current = current >= range.length ? (current - range.length) : current;
            }

            return results;
        } catch (Exception e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
