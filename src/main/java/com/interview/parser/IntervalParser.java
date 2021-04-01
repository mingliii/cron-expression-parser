package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

public class IntervalParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains("/");
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
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
                if (stream(range).noneMatch(val -> val == start)) {
                    throw new NotValidCronExpressionException(fieldErrorMsg(field));
                }
            }

            int interval = parseInt(startEnd[1].trim());
            if (start > range[range.length - 1] || interval < 1) {
                throw new NotValidCronExpressionException(fieldErrorMsg(field));
            }

            List<Integer> results = new ArrayList<>();

            // handle cases like below
            // [0...59]  1/45 => [1, 46, 31, 16]
            // [0...59]  0/15 => [0, 15, 30, 45]
            int mark = 0;
            int mod = range.length;
            results.add(start);
            for (int i = mark; i < range.length; i++) {
                int val = range[i];
                if (val % interval == 0) {
                    mark = (val + start) >= mod ? (val + start - mod) % mod : val + start;
                    if (!results.contains(mark)) {
                        results.add(mark);
                    }
                }
            }
            while (!results.contains((mark + interval) % mod)) {
                mark = (mark + interval) % mod;
                results.add(mark);
            }

            return results.stream().map(String::valueOf).toArray(String[]::new);
        } catch (Exception e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
