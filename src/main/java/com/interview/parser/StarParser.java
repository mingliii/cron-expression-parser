package com.interview.parser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.interview.parser.FieldParser.FieldType.COMMAND;
import static java.util.Arrays.stream;

public class StarParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return Objects.equals(field, "*") && fieldType != COMMAND;
    }

    @Override
    public List<String> doParse(String field, FieldType fieldType) {
        return stream(VALUES_MAP.get(fieldType)).mapToObj(String::valueOf).collect(Collectors.toList());
    }
}
