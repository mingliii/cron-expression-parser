package com.interview.parser;

import com.interview.NotvalidCronExpressionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntervalParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains("/");
    }

    @Override
    public String[] parse(String field, FieldType fieldType) {
        int[] range = VALUES_MAP.get(fieldType);
        final String[] startEnd = field.split("/");

        if (startEnd.length != 2) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field));
        }

        try {
            int start = Objects.equals(startEnd[0], "*") ? 0 : Integer.parseInt(startEnd[0]);
            int interval = Integer.parseInt(startEnd[1]);
            if (start > range[range.length - 1] || interval < 1) {
                throw new NotvalidCronExpressionException(fieldErrorMsg(field));
            }

            int mod = range.length;
            int mark = 0;

            List<Integer> results = new ArrayList<>();

            // [0...59]  1/45 => [1, 46, 31, 16]
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
            throw new NotvalidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
