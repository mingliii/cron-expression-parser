package com.interview;

import com.interview.parser.*;
import com.interview.parser.FieldParser.FieldType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;

public class CronExpressionParser {

    private final List<FieldParser> fieldParsers = of(
            new CommandParser(),
            new StarParser(),
            new QuestionMarkParser(),
            new RangeParser(),
            new IntervalParser(),
            new ListParser(),
            new DefaultParser()
    );

    public CronExpressionResult parse(String expression) {
        String[] fields = expression.split("[\\s\\n]+");

        if (fields.length < 6) {
            throw new NotValidCronExpressionException(expression, "The number of fields should be 6, given: " + fields.length);
        }

        CronExpressionResult results = new CronExpressionResult();

        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            FieldType fieldType = FieldType.values()[i];

            if (fieldType == FieldType.COMMAND) {
                if (i >= 5) {
                    field = String.join("", Arrays.asList(fields).subList(5, fields.length));
                    String finalField = field;
                    FieldParser fieldParser = fieldParsers.stream().filter(parser -> parser.match(finalField, fieldType)).findFirst()
                            .orElseThrow(() -> new NotValidCronExpressionException("Invalid expression: " + expression));
                    results.set(fieldType, fieldParser.parse(field, fieldType));
                    break;
                }
            } else {
                String finalField = field;
                FieldParser fieldParser = fieldParsers.stream().filter(parser -> parser.match(finalField, fieldType)).findFirst()
                        .orElseThrow(() -> new NotValidCronExpressionException("Invalid expression: " + expression));
                results.set(fieldType, fieldParser.parse(field, fieldType));
            }
        }

        return results;
    }
}
