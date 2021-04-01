package com.interview.validator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ming Li
 */
public class RangeValidatorTest {

    private final RangeValidator rangeValidator = new RangeValidator();

    @Test
    public void testValidate() {
        int[] months = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        Assert.assertTrue(rangeValidator.validate(months, 8));
        Assert.assertTrue(rangeValidator.validate(months, 1, 4, 8, 10));

        Assert.assertFalse(rangeValidator.validate(months, 0));
        Assert.assertFalse(rangeValidator.validate(months, 13));
        Assert.assertFalse(rangeValidator.validate(months, 1, 4, 8, 13));
    }
}
