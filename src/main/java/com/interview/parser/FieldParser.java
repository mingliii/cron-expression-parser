package com.interview.parser;

import java.util.HashMap;
import java.util.Map;

import static com.interview.parser.FieldParser.FieldType.*;
import static java.lang.String.format;

public abstract class FieldParser {

    // days in week - ignore the first one
    private static final int[] _DAYS_OF_WEEK = new int[7];
    // 12 months - ignore the first one
    private static final int[] _MONTHS = new int[12];
    // 31 days in month - ignore the first one
    private static final int[] _DAYS_OF_MONTH = new int[31];
    // 24 hours - ignore the first one
    private static final int[] _HOURS = new int[24];
    // 60 minutes in an hour
    private static final int[] _MINUTES = new int[60];

    static {
        init(1, _DAYS_OF_WEEK, _MONTHS, _DAYS_OF_MONTH);
        init(0, _HOURS, _MINUTES);
    }

    private static void init(int offset, int[]... values) {
        for (int[] value : values) {
            for (int i = 0; i < value.length; i++) {
                value[i] = i + offset;
            }
        }
    }

    public enum FieldType {
        MINUTE,
        HOUR,
        DAY_OF_MONTH,
        MONTH,
        DAY_OF_WEEK,
        COMMAND
    }

    // Mapping from field types to value ranges
    static final Map<FieldType, int[]> VALUES_MAP = new HashMap<>(5) {
        {
            put(MINUTE, _MINUTES);
            put(HOUR, _HOURS);
            put(DAY_OF_MONTH, _DAYS_OF_MONTH);
            put(MONTH, _MONTHS);
            put(DAY_OF_WEEK, _DAYS_OF_WEEK);
        }
    };

    /**
     * Determine if a parser should be matched to do parse. A match doesn't mean success of parsing.
     * @param field field to match
     * @param fieldType field type
     * @return if a parse is matched
     */
    public abstract boolean match(String field, FieldType fieldType) ;

    /**
     * Template method to make sure a parser is matched before parsing
     * @param field field to match and parse
     * @param fieldType field type
     * @return a list of parsed values
     */
    public String[] parse(String field, FieldType fieldType) {
        assert match(field, fieldType) : "Field parser must match";

        return doParse(field, fieldType);
    }

    /**
     * Parse the given field with its type
     * @param field field to parse
     * @param fieldType field type
     * @return a list of parsed values
     */
    abstract String[] doParse(String field, FieldType fieldType);

    String fieldErrorMsg(String field) {
        return format("The field '%s' is not valid", field);
    }
}
