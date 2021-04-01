package com.interview.parser;

import java.util.Objects;

public class QuestionMarkParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return Objects.equals(field, "?");
    }

    @Override
    public String[] parse(String field, FieldType fieldType) {
        return new String[0];
    }
}
