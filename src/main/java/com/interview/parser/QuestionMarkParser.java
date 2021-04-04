package com.interview.parser;

import com.interview.NotValidCronExpressionException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.interview.parser.FieldParser.FieldType.DAY_OF_MONTH;
import static com.interview.parser.FieldParser.FieldType.DAY_OF_WEEK;
import static java.util.Collections.emptyList;

public class QuestionMarkParser extends FieldParser {

    @Override
    public boolean match(String field, FieldType fieldType) {
        return Objects.equals(field, "?");
    }

    @Override
    public List<String> doParse(String field, FieldType fieldType) {
        if(fieldType == DAY_OF_MONTH || fieldType == DAY_OF_WEEK) {
            return emptyList();
        }

        throw new NotValidCronExpressionException(fieldErrorMsg(field));
    }
}
