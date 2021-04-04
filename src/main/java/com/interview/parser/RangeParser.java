package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.interview.parser.FieldParser.FieldType.*;
import static java.util.Arrays.*;
import static java.util.Arrays.asList;

public class RangeParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains("-") && fieldType != COMMAND;
    }

    @Override
    public List<String> doParse(String field, FieldType fieldType) {
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

            rangeValidator.validate(field, range, start, end);

            // Handle the case like 23-2
            return copyList(values, start - offset, end - offset);
        } catch (NumberFormatException e) {
            String start = startEnd[0];
            String end = startEnd[1];
            if (fieldType == DAY_OF_WEEK) {
                rangeValidator.validate(field, _DAYS_OF_WEEK_DDD, startEnd);
                return copyList(_DAYS_OF_WEEK_DDD.toArray(String[]::new), _DAYS_OF_WEEK_DDD.indexOf(start), _DAYS_OF_WEEK_DDD.indexOf(end));
            } else if (fieldType == DAY_OF_MONTH) {
                rangeValidator.validate(field, _MONTH_MMM, startEnd);
                return copyList(_MONTH_MMM.toArray(String[]::new), _DAYS_OF_WEEK_DDD.indexOf(start), _DAYS_OF_WEEK_DDD.indexOf(end));
            }
            throw new NotValidCronExpressionException(fieldErrorMsg(field));
        }
    }

    List<String> copyList(String[] values, int start, int end) {
        if (start > end) {
            List<String> results = new ArrayList<>(Arrays.asList(copyOfRange(values, start, values.length)));
            List<String> rest = new ArrayList<>(Arrays.asList(copyOfRange(values, 0, end + 1)));
            results.addAll(rest);
            return  results;
        }
        return Arrays.asList(values).subList(start, end + 1);
    }
}
