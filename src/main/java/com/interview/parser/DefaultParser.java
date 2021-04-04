package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.List;

import static com.interview.parser.FieldParser.FieldType.COMMAND;
import static java.lang.Integer.parseInt;
import static java.util.List.of;

/**
 * This parser should be placed at the last as it will match everything except command
 */
public class DefaultParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return fieldType != COMMAND && SPECIAL_CHARACTERS.stream().noneMatch(field::contains);
    }

    @Override
    public List<String> doParse(String field, FieldType fieldType) {
        try {
            int[] ranges = VALUES_MAP.get(fieldType);

            int val = parseInt(field);

            if (!rangeValidator.validate(ranges, val)) {
                throw new NotValidCronExpressionException(fieldErrorMsg(field));
            }

            return of(String.valueOf(val));
        } catch (NumberFormatException e) {
            throw new NotValidCronExpressionException(fieldErrorMsg(field), e);
        }
    }
}
