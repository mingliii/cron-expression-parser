package com.interview.parser;

import com.interview.CronExpression;
import com.interview.CronExpression.FieldType;
import com.interview.NotvalidCronExpressionException;

import java.lang.reflect.Field;

import static com.interview.CronExpression.FieldType.HOUR;
import static com.interview.CronExpression.FieldType.MINUTE;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class RangeParser implements Parser {

    @Override
    public boolean match(String field) {
        return field.contains("-");
    }

    @Override
    public String[] parse(String field, FieldType fieldType,  int[] range) {
        final String[] values = stream(range).mapToObj(String::valueOf).toArray(String[]::new);
        String[] startEnd = field.split("-");
        if (startEnd.length != 2) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field));
        }

        int offset = (fieldType == MINUTE || fieldType == HOUR) ? 0 : 1;

        try {
            int start = Integer.parseInt(startEnd[0]) - offset;
            int end = Integer.parseInt(startEnd[1]) - offset;

            if (start > end || start < 0 || end >= range.length) {
                throw new NotvalidCronExpressionException(fieldErrorMsg(field));
            }

            return copyOfRange(values, start, end + 1);
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field));
        }
    }
}
