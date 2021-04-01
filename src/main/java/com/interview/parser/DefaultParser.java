package com.interview.parser;

import com.interview.NotvalidCronExpressionException;

import java.util.Arrays;

import static com.interview.parser.FieldParser.FieldType.COMMAND;

/**
 * This parser should be placed at the last as it will match everything except command
 */
public class DefaultParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return fieldType != COMMAND;
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
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
