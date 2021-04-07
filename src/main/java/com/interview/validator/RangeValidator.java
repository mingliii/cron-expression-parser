package com.interview.validator;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.stream;

/**
 *
 * @author Ming Li
 */
public class RangeValidator {

    /**
     * Validate all given values that fall into the range
     */
    public boolean validate(int[] range, int ...values) {
        return values.length > 0 && stream(values).allMatch(value -> stream(range).anyMatch(val -> Objects.equals(value, val)));
    }

    public <T> boolean validate(T[] range, T ...values) {
        return values.length > 0 && stream(values).allMatch(value -> Arrays.asList(range).contains(value));
    }
}
