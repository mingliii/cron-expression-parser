package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.interview.parser.FieldParser.FieldType.COMMAND;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class RangeParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains("-") && fieldType != COMMAND;
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
        int[] range = VALUES_MAP.get(fieldType);
        final String[] values = stream(range).mapToObj(String::valueOf).toArray(String[]::new);
        String[] startEnd = field.split("-");

        if (startEnd.length != 2) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field));
        }

        try {
            int offset = values[0].equals("0") ? 0 : 1;
            int start = Integer.parseInt(startEnd[0]);
            int end = Integer.parseInt(startEnd[1]);

            if (!rangeValidator.validate(range, start, end)) {
                throw new NotValidCronExpressionException(fieldErrorMsg(field));
            }

            // Handle the case like 23-2
            if (start > end) {
                List<String> results = new ArrayList<>(Arrays.asList(copyOfRange(values, start - offset, values.length)));
                List<String> rest = new ArrayList<>(Arrays.asList(copyOfRange(values, 0, end + 1 - offset)));
                results.addAll(rest);
                return  results.toArray(String[]::new);
            }

            return copyOfRange(values, start - offset, end + 1 - offset);
        } catch (NumberFormatException e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field));
        }
    }
}
