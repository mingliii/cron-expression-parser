package com.interview.parser;

import com.interview.NotvalidCronExpressionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.interview.parser.FieldParser.FieldType.HOUR;
import static com.interview.parser.FieldParser.FieldType.MINUTE;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class RangeParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains("-");
    }

    @Override
    public String[] parse(String field, FieldType fieldType) {
        final String[] values = stream(VALUES_MAP.get(fieldType)).mapToObj(String::valueOf).toArray(String[]::new);
        String[] startEnd = field.split("-");

        if (startEnd.length != 2) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field));
        }


        try {
            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);

            if (stream(values).noneMatch(val -> Integer.parseInt(val) == start) || stream(values).noneMatch(val -> Integer.parseInt(val) == end)) {
                throw new NotvalidCronExpressionException(fieldErrorMsg(field));
            }

            // Handle the case like 23-2
            if (start > end) {
                List<String> results = new ArrayList<>(Arrays.asList(copyOfRange(values, start, values.length)));
                List<String> rest = new ArrayList<>(Arrays.asList(copyOfRange(values, 0, end + 1)));
                results.addAll(rest);
                return  results.toArray(String[]::new);
            }

            return copyOfRange(values, start, end + 1);
        } catch (NumberFormatException e) {
            throw new NotvalidCronExpressionException(fieldErrorMsg(field));
        }
    }
}
