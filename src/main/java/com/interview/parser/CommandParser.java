package com.interview.parser;

import java.util.List;

import static com.interview.parser.FieldParser.FieldType.COMMAND;
import static java.util.List.of;

public class CommandParser extends FieldParser{

    @Override
    public boolean match(String field, FieldType fieldType) {
        return fieldType == COMMAND;
    }

    @Override
    public List<String> doParse(String field, FieldType fieldType) {
        return of(field);
    }
}
