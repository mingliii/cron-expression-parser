package com.interview.validator;

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
        return stream(values).allMatch(value -> stream(range).anyMatch(val -> Objects.equals(value, val)));
    }
}
