package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.Objects;

import static com.interview.parser.FieldParser.FieldType.DAY_OF_MONTH;
import static com.interview.parser.FieldParser.FieldType.DAY_OF_WEEK;

public class QuestionMarkParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return Objects.equals(field, "?");
    }

    @Override
    public String[] doParse(String field, FieldType fieldType) {
        if(fieldType == DAY_OF_MONTH || fieldType == DAY_OF_WEEK) {
            return new String[0];
        }

        throw new NotValidCronExpressionException(fieldErrorMsg(field));
    }
}
