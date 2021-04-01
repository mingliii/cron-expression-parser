package com.interview.parser;

import static com.interview.parser.FieldParser.FieldType.COMMAND;

public class CommandParser extends FieldParser{

    @Override
    public boolean match(String field, FieldType fieldType) {
        return fieldType == COMMAND;
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
        return new String[]{field};
    }
}
