package com.interview.parser;

import com.interview.NotvalidCronExpressionException;

import static com.interview.CronExpression.FieldType;
import static java.util.Arrays.stream;

public class ListParser implements Parser{

    @Override
    public boolean match(String field) {
        return field.contains(",");
    }

    @Override
    public String[] parse(String field, FieldType fieldType, int[] range) {
        try {
            String[] values = field.split(",");
            return stream(values).mapToInt(Integer::parseInt).distinct().mapToObj(String::valueOf).toArray(String[]::new);
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
