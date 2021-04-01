package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import static com.interview.parser.FieldParser.FieldType.COMMAND;
import static java.util.Arrays.stream;

public class ListParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return field.contains(",") && fieldType != COMMAND;
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
        try {
            int[] range = VALUES_MAP.get(fieldType);
            String[] values = field.split(",");
            values = stream(values).mapToInt(val -> Integer.parseInt(val.trim())).distinct().mapToObj(String::valueOf).toArray(String[]::new);

            if (!stream(values).allMatch(s -> stream(range).anyMatch(val -> Integer.parseInt(s) == val))) {
                throw new NotValidCronExpressionException(fieldErrorMsg(field));
            }

            return values;
        } catch (NumberFormatException e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
