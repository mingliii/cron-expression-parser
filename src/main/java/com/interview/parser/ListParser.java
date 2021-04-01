package com.interview.parser;

import com.interview.NotvalidCronExpressionException;

import static java.util.Arrays.stream;

public class ListParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains(",");
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
        try {
            int[] range = VALUES_MAP.get(fieldType);
            String[] values = field.split(",");
            values = stream(values).mapToInt(Integer::parseInt).distinct().mapToObj(String::valueOf).toArray(String[]::new);

            if (!stream(values).allMatch(s -> stream(range).anyMatch(val -> Integer.parseInt(s) == val))) {
                throw new NotvalidCronExpressionException(fieldErrorMsg(field));
            }

            return stream(values).mapToInt(Integer::parseInt).distinct().mapToObj(String::valueOf).toArray(String[]::new);
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
