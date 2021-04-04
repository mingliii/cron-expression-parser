package com.interview.validator;

import com.interview.NotValidCronExpressionException;

import java.util.Collection;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Arrays.stream;

/**
 *
 * @author Ming Li
 */
public class RangeValidator {

    /**
     * Validate all given values that fall into the range
     */
    public void validate(String field, int[] range, int ...values) {
        if(values.length > 0 && stream(values).allMatch(value -> stream(range).anyMatch(val -> Objects.equals(value, val)))) {
            return;
        }

        throw new NotValidCronExpressionException(fieldErrorMsg(field));
    }

    public <T> void validate(String field, Collection<T> range, T ...values) {
        if (values.length > 0 && stream(values).allMatch(range::contains)) {
            return;
        }

        throw new NotValidCronExpressionException(fieldErrorMsg(field));
    }

    String fieldErrorMsg(String field) {
        return format("The field '%s' is not valid", field);
    }
}
