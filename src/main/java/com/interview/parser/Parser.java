package com.interview.parser;

import com.interview.CronExpression;

import static com.interview.CronExpression.*;
import static java.lang.String.format;

public interface Parser {

    boolean match(String field);

    String[] parse(String field, FieldType fieldType, int[] range);

    default String fieldErrorMsg(String field) {
        return format("The field '%s' is not valid", field);
    }
}
