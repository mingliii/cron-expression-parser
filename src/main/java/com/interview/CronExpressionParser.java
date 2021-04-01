package com.interview;

import com.interview.parser.*;
import com.interview.parser.FieldParser.FieldType;

import java.util.List;

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

    public CronExpressionResults parse(String expression) {
        String[] fields = expression.split("\\s");

        if (fields.length != 6) {
            throw new NotValidCronExpressionException(expression, "The number of fields should be 6, given: " + fields.length);
        }

        CronExpressionResults results = new CronExpressionResults();

        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            FieldType fieldType = FieldType.values()[i];
            FieldParser fieldParser = fieldParsers.stream().filter(parser -> parser.match(field, fieldType)).findFirst()
                    .orElseThrow(() -> new NotValidCronExpressionException("Invalid expression: " + expression));
            results.set(fieldType, fieldParser.parse(field, fieldType));
        }

        return results;
    }
}
