package com.interview.validator;

import com.interview.NotValidCronExpressionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ming Li
 */
public class RangeValidatorTest {

    private final RangeValidator rangeValidator = new RangeValidator();

    @Test
    public void testValid() {
        int[] months = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        rangeValidator.validate("1/2", months, 8);
        rangeValidator.validate("1/2", months, 1, 4, 8, 10);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testInvalid_BelowRange() {
        int[] months = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        rangeValidator.validate("1/2", months, 0);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testInvalid_BeyondRange() {
        int[] months = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        rangeValidator.validate("1/2", months, 13);
    }

    @Test(expected = NotValidCronExpressionException.class)
    public void testInvalid_NotProvided() {
        int[] months = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        rangeValidator.validate("1/2", months);
    }
}
