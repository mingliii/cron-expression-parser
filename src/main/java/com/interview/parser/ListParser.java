package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.Arrays;
import java.util.List;

import static com.interview.parser.FieldParser.FieldType.*;
import static java.util.Arrays.stream;

public class ListParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains(",") && fieldType != COMMAND;
    }

    @Override
    public List<String> doParse(String field, FieldType fieldType) {
        int[] range = VALUES_MAP.get(fieldType);
        String[] values = stream(field.split(",")).map(String::trim).toArray(String[]::new);
        try {
            values = stream(values).mapToInt(Integer::parseInt).distinct().mapToObj(String::valueOf).toArray(String[]::new);
            int[] intValues = stream(values).mapToInt(val -> Integer.parseInt(val.trim())).distinct().toArray();
            if (!rangeValidator.validate(range, intValues)) {
                throw new NotValidCronExpressionException(fieldErrorMsg(field));
            }

            return Arrays.asList(values);
        } catch (NumberFormatException e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
