package com.interview.parser;

import java.util.Objects;

import static com.interview.parser.FieldParser.FieldType.COMMAND;
import static java.util.Arrays.stream;

public class StarParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return Objects.equals(field, "*") && fieldType != COMMAND;
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
        return stream(VALUES_MAP.get(fieldType)).mapToObj(String::valueOf).toArray(String[]::new);
    }
}
