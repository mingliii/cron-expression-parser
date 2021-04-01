package com.interview.parser;

import com.interview.NotvalidCronExpressionException;

import java.util.Arrays;

public class SimpleParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return true;
    }

    @Override
    public String[] parse(String field, FieldType fieldType) {
        int[] ranges = VALUES_MAP.get(fieldType);

        try {
            int val = Integer.parseInt(field);
            if (Arrays.stream(ranges).noneMatch(value -> val == value)) {
                throw new NotvalidCronExpressionException(fieldErrorMsg(field));
            }

            return new String[]{String.valueOf(Integer.parseInt(field))};
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
